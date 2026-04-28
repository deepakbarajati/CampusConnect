import api from './client'

export interface PagedResponse<T> {
  content: T[]
}

export interface ClassroomDTO {
  id: number
  teacherId: number
  subject: string
  semester: number
  scheduleId?: number
  description?: string
}

export interface AssignmentDTO {
  id: number
  classroomId: number
  title: string
  description: string
  deadline: string
}

export interface AttendanceDTO {
  id: number
  studentId: number
  classroomId: number
  status: 'PRESENT' | 'ABSENT' | 'LATE'
  date: string
}

export interface SubmissionDTO {
  id: number
  assignmentId: number
  studentId: number
  grade?: string
}

export const classroomService = {
  getClassrooms: (page = 0, size = 10, subject?: string, semester?: number) => {
    const params = { page, size, ...(subject && { subject }), ...(semester && { semester }) }
    return api.get<ClassroomDTO[] | PagedResponse<ClassroomDTO>>('/classrooms', { params })
  },

  getClassroom: (id: number) =>
    api.get<ClassroomDTO>(`/classrooms/${id}`),

  createClassroom: (data: Omit<ClassroomDTO, 'id' | 'scheduleId'>) =>
    api.post<ClassroomDTO>('/classrooms', data),

  updateClassroom: (id: number, data: Partial<ClassroomDTO>) =>
    api.put<ClassroomDTO>(`/classrooms/${id}`, data),

  deleteClassroom: (id: number) =>
    api.delete(`/classrooms/${id}`),

  // Assignments
  getAssignments: (classroomId: number) =>
    api.get<AssignmentDTO[]>(`/assignments/classroom/${classroomId}`),

  createAssignment: (data: Omit<AssignmentDTO, 'id'>) =>
    api.post<AssignmentDTO>('/assignments', data),

  // Attendance
  markAttendance: (studentId: number, classroomId: number, status: string) =>
    api.post(`/attendance/student/${studentId}/classroom/${classroomId}/${status}`),

  getAttendance: (studentId: number) =>
    api.get<AttendanceDTO[]>(`/attendance/student/${studentId}`),

  // Submissions
  submitAssignment: (assignmentId: number) =>
    api.post<SubmissionDTO>(`/submissions/assignment/${assignmentId}`),

  getSubmissions: (assignmentId: number) =>
    api.get<SubmissionDTO[]>(`/submissions/assignment/${assignmentId}`),
}
