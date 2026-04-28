import 'jwt-decode'

declare module 'jwt-decode' {
  export interface JwtPayload {
    userId?: string
    email?: string
    role?: string
    name?: string
  }
}

