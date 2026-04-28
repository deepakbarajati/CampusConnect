import api from './client'

export interface LoginRequest {
  identifier?: string
  email?: string
  password: string
}

export interface RegisterRequest {
  username: string
  email: string
  password: string
  role: 'STUDENT' | 'TEACHER' | 'ALUMNI' | 'ADMIN'
}

export interface AuthResponse {
  id: number
  username: string
  email: string
  role: string
}

export const authService = {
  login: (credentials: LoginRequest) => {
    const identifier = credentials.identifier || credentials.email || ''
    return api.post<string>('/auth/login', {
      identifier,
      password: credentials.password,
    })
  },

  register: (data: RegisterRequest) =>
    api.post<AuthResponse>('/auth/signup', data),

  refresh: () =>
    api.get<string>('/auth/refresh'),

  changePassword: (currentPassword: string, newPassword: string) =>
    api.put('/auth/change-password', { oldPassword: currentPassword, newPassword }),

  verifyEmail: (token: string) =>
    api.get(`/auth/verify?token=${token}`),

  forgotPassword: (email: string) =>
    api.post('/auth/forgot-password', { email }),

  resetPassword: (token: string, newPassword: string) =>
    api.post('/auth/reset-password', { token, newPassword }),
}

const isObject = (value: unknown): value is Record<string, unknown> =>
  typeof value === 'object' && value !== null

const readToken = (value: unknown): string | null => {
  if (typeof value === 'string' && value.trim().length > 0) {
    return value
  }

  if (!isObject(value)) {
    return null
  }

  const token = value.token ?? value.accessToken
  if (typeof token === 'string' && token.trim().length > 0) {
    return token
  }

  if ('data' in value) {
    return readToken(value.data)
  }

  return null
}

export const extractAuthToken = (payload: unknown): string | null => readToken(payload)
