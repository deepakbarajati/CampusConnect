import api from './client'

export interface ForumDTO {
  id: number
  title: string
  description: string
  createdBy: number
  createdAt: string
}

export interface ForumReplyDTO {
  id: number
  forumId: number
  userId: number
  message: string
  timestamp: string
}

export interface CategoryDTO {
  id: number
  name: string
  description: string
}

export const forumService = {
  getForums: () => api.get<ForumDTO[]>('/forums'),

  getForum: (id: number) =>
    api.get<ForumDTO>(`/forums/${id}`),

  getUserForums: (userId: number) =>
    api.get<ForumDTO[]>(`/forums/user/${userId}`),

  createForum: (data: Omit<ForumDTO, 'id' | 'createdAt'>) =>
    api.post<ForumDTO>('/forums', data),

  updateForum: (id: number, data: Partial<ForumDTO>) =>
    api.put<ForumDTO>(`/forums/${id}`, data),

  deleteForum: (id: number) =>
    api.delete(`/forums/${id}`),

  // Replies
  getReplies: (forumId: number) =>
    api.get<ForumReplyDTO[]>(`/forums/${forumId}/replies`),

  getUserReplies: (userId: number) =>
    api.get<ForumReplyDTO[]>(`/forums/replies/user/${userId}`),

  createReply: (forumId: number, data: Omit<ForumReplyDTO, 'id' | 'timestamp' | 'forumId'>) =>
    api.post<ForumReplyDTO>(`/forums/${forumId}/replies`, data),

  deleteReply: (replyId: number) =>
    api.delete(`/forums/replies/${replyId}`),

  // Categories
  getCategories: () =>
    api.get<CategoryDTO[]>('/forums/categories'),
}
