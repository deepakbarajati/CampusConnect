import api from './client'

export interface OpportunityDTO {
  id: string
  title: string
  description: string
  company: string
  position: string
  salary?: number
  location?: string
  isActive: boolean
  postedAt: string
  deadline?: string
}

export interface ApplicationDTO {
  id: string
  opportunityId: string
  userId: string
  status: 'PENDING' | 'APPLIED' | 'REVIEWED' | 'SELECTED' | 'REJECTED'
  appliedAt: string
  resumeUrl?: string
}

export const opportunityService = {
  getOpportunities: (page = 0, size = 10) =>
    api.get<OpportunityDTO[]>('/opportunities', { params: { page, size } }),

  getActiveOpportunities: () =>
    api.get<OpportunityDTO[]>('/opportunities/active'),

  getOpportunity: (id: string) =>
    api.get<OpportunityDTO>(`/opportunities/${id}`),

  createOpportunity: (data: Omit<OpportunityDTO, 'id' | 'postedAt'>) =>
    api.post<OpportunityDTO>('/opportunities', data),

  updateOpportunity: (id: string, data: Partial<OpportunityDTO>) =>
    api.put<OpportunityDTO>(`/opportunities/${id}`, data),

  deleteOpportunity: (id: string) =>
    api.delete(`/opportunities/${id}`),

  searchOpportunities: (title: string) =>
    api.get<OpportunityDTO[]>('/opportunities/search', { params: { title } }),

  // Applications
  applyForOpportunity: (applicationDTO: ApplicationDTO) =>
    api.post<ApplicationDTO>('/opportunities/apply', applicationDTO),

  getApplications: (opportunityId: string) =>
    api.get<ApplicationDTO[]>(`/opportunities/${opportunityId}/applications`),

  getApplicationsByStatus: (status: string) =>
    api.get<ApplicationDTO[]>(`/opportunities/applications/status/${status}`),
}
