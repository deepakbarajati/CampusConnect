import axios, { AxiosError, AxiosInstance } from 'axios'
import Cookies from 'js-cookie'
import { useAuthStore } from '../store/authStore'

interface ApiEnvelope<T> {
  data: T
  error?: unknown
  timeStamp?: string
}

const API_BASE_URL = import.meta.env.VITE_API_URL || 'http://localhost:8080/api'

const isObject = (value: unknown): value is Record<string, unknown> =>
  typeof value === 'object' && value !== null

const isApiEnvelope = <T>(value: unknown): value is ApiEnvelope<T> =>
  isObject(value) && 'data' in value && ('timeStamp' in value || 'error' in value)

const api: AxiosInstance = axios.create({
  baseURL: API_BASE_URL,
  withCredentials: true,
})

// Add token to requests
api.interceptors.request.use(
  (config) => {
    const token = Cookies.get('authToken')
    if (token) {
      config.headers = config.headers ?? {}
      config.headers.Authorization = `Bearer ${token}`
    }
    return config
  },
  (error) => Promise.reject(error)
)

// Handle responses
api.interceptors.response.use(
  (response) => {
    if (isApiEnvelope(response.data)) {
      response.data = response.data.data
    }
    return response
  },
  (error) => {
    if (error.response?.status === 401) {
      useAuthStore.getState().logout()

      const currentPath = window.location.pathname
      if (currentPath !== '/login' && currentPath !== '/register') {
        window.location.assign('/login')
      }
    }
    return Promise.reject(error)
  }
)

export const getErrorMessage = (error: unknown, fallback: string): string => {
  if (axios.isAxiosError(error)) {
    const responseData = error.response?.data

    if (typeof responseData === 'string' && responseData.trim().length > 0) {
      return responseData
    }

    if (isObject(responseData)) {
      const message = responseData.message
      if (typeof message === 'string' && message.trim().length > 0) {
        return message
      }

      const nestedError = responseData.error
      if (isObject(nestedError) && typeof nestedError.message === 'string' && nestedError.message.trim().length > 0) {
        return nestedError.message
      }
    }
  }

  if (error instanceof Error && error.message.trim().length > 0) {
    return error.message
  }

  return fallback
}

export const normalizeListData = <T>(payload: T[] | { content?: T[] } | undefined): T[] => {
  if (!payload) {
    return []
  }

  if (Array.isArray(payload)) {
    return payload
  }

  return Array.isArray(payload.content) ? payload.content : []
}

export const isNotFoundOrMethodError = (error: unknown): boolean => {
  if (!axios.isAxiosError(error)) {
    return false
  }

  const status = (error as AxiosError).response?.status
  return status === 404 || status === 405
}

export default api

