import api from './client'

export type ChatType = 'PRIVATE' | 'GROUP'
export type MessageType = 'TEXT' | 'IMAGE' | 'FILE'

export interface ChatRoomDTO {
  id: string
  name: string
  createdBy: number
  type: ChatType
}

export interface MessageDTO {
  id: string
  chatRoomId: string
  receiverId?: number
  content: string
  type: MessageType
  sentAt: string
  isRead: boolean
}

export const chatService = {
  getRooms: () =>
    api.get<ChatRoomDTO[]>('/chatRoom'),

  getRoomById: (roomId: string) =>
    api.get<ChatRoomDTO>(`/chatRoom/${roomId}`),

  createRoom: (data: { name: string; type: ChatType }) =>
    api.post<ChatRoomDTO>('/chatRoom', data),

  updateRoom: (roomId: string, data: Partial<ChatRoomDTO>) =>
    api.put<ChatRoomDTO>(`/chatRoom/${roomId}`, data),

  deleteRoom: (roomId: string) =>
    api.delete(`/chatRoom/${roomId}`),

  getMessages: (chatRoomId: string) =>
    api.get<MessageDTO[]>(`/messages/chatRoom/${chatRoomId}`),

  sendMessage: (chatRoomId: string, content: string, type: MessageType = 'TEXT') =>
    api.post<MessageDTO>('/messages', { chatRoomId, content, type }),

  markMessageRead: (messageId: string) =>
    api.patch<MessageDTO>(`/messages/${messageId}/read`),

  markAllRead: (chatRoomId: string, userId: number) =>
    api.patch<MessageDTO[]>(`/messages/chat/${chatRoomId}/user/${userId}/read`),
}
