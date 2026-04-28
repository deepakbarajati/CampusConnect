import api from './client'

export interface GamificationDTO {
  userId: string
  points: number
  level: number
}

export interface LeaderboardDTO {
  rank: number
  userId: string
  userName: string
  points: number
  level: number
}

export interface BadgeDTO {
  id: string
  name: string
  description: string
  icon?: string
  userId: string
  earnedAt: string
}

export const gamificationService = {
  getUserPoints: (userId: string) =>
    api.get<GamificationDTO>(`/gamification/points/user/${userId}`),

  getTotalPoints: (userId: string) =>
    api.get<{ totalPoints: number }>(`/gamification/points/total/${userId}`),

  addPoints: (userId: string, points: number, reason?: string) =>
    api.post('/gamification/points', { userId, points, reason }),

  getLeaderboard: (semester?: number, limit = 10) =>
    api.get<LeaderboardDTO[]>('/gamification/leaderboard', { params: { semester, limit } }),

  updateLeaderboard: () =>
    api.post('/gamification/leaderboard/update'),

  // Badges
  getUserBadges: (userId: string) =>
    api.get<BadgeDTO[]>(`/gamification/badges/user/${userId}`),

  awardBadge: (userId: string, badgeName: string) =>
    api.post(`/gamification/badge/${userId}`, { badgeName }),
}

