import { create } from 'zustand'
import Cookies from 'js-cookie'
import { jwtDecode } from 'jwt-decode'

interface User {
  id: string
  email: string
  role: string
  name?: string
}

interface TokenPayload {
  sub?: string
  userId?: string
  email?: string
  role?: string
  name?: string
  username?: string
  exp?: number
}

interface AuthStore {
  user: User | null
  token: string | null
  isAuthenticated: boolean
  login: (token: string) => void
  logout: () => void
  setUser: (user: User) => void
}

const parseToken = (token: string): User | null => {
  try {
    const decoded = jwtDecode<TokenPayload>(token)
    const id = decoded.userId || decoded.sub

    if (!id) {
      return null
    }

    return {
      id,
      email: decoded.email || '',
      role: decoded.role || 'STUDENT',
      name: decoded.name || decoded.username,
    }
  } catch {
    return null
  }
}

const isTokenExpired = (token: string): boolean => {
  try {
    const decoded = jwtDecode<TokenPayload>(token)
    if (!decoded.exp) {
      return false
    }

    return decoded.exp * 1000 <= Date.now()
  } catch {
    return true
  }
}

export const useAuthStore = create<AuthStore>((set) => {
  const tokenFromCookie = Cookies.get('authToken')
  const tokenIsValid = !!tokenFromCookie && !isTokenExpired(tokenFromCookie)
  const token = tokenIsValid ? tokenFromCookie : null
  const initialUser = token ? parseToken(token) : null

  if (!tokenIsValid && tokenFromCookie) {
    Cookies.remove('authToken')
  }

  return {
    user: initialUser,
    token,
    isAuthenticated: !!token && !!initialUser,

    login: (token: string) => {
      if (isTokenExpired(token)) {
        Cookies.remove('authToken')
        set({ token: null, user: null, isAuthenticated: false })
        return
      }

      Cookies.set('authToken', token, {
        expires: 7,
        sameSite: 'lax',
        secure: window.location.protocol === 'https:',
      })

      const user = parseToken(token)
      if (!user) {
        Cookies.remove('authToken')
        set({ token: null, user: null, isAuthenticated: false })
        return
      }

      set({ token, user, isAuthenticated: true })
    },

    logout: () => {
      Cookies.remove('authToken')
      set({ token: null, user: null, isAuthenticated: false })
    },

    setUser: (user: User) => {
      set({ user })
    },
  }
})
