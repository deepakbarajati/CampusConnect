import { useState, useEffect } from 'react'
import {
  Container,
  Paper,
  Box,
  Typography,
  Button,
  Dialog,
  TextField,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Alert,
  CircularProgress,
  MenuItem,
} from '@mui/material'
import AddIcon from '@mui/icons-material/Add'
import { classroomService, ClassroomDTO } from '../api/classroomService'

export default function Classroom() {
  const [classrooms, setClassrooms] = useState<ClassroomDTO[]>([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState('')
  const [openDialog, setOpenDialog] = useState(false)
  const [formData, setFormData] = useState({
    name: '',
    subject: '',
    semester: 1,
  })

  useEffect(() => {
    fetchClassrooms()
  }, [])

  const fetchClassrooms = async () => {
    try {
      const response = await classroomService.getClassrooms(0, 20)
      setClassrooms(response.data)
      setError('')
    } catch (err: any) {
      setError('Failed to load classrooms')
      console.error(err)
    } finally {
      setLoading(false)
    }
  }

  const handleCreate = async () => {
    try {
      await classroomService.createClassroom({
        ...formData,
        professorId: '',
      })
      setOpenDialog(false)
      setFormData({ name: '', subject: '', semester: 1 })
      await fetchClassrooms()
    } catch (err: any) {
      setError('Failed to create classroom')
    }
  }

  if (loading) {
    return (
      <Container sx={{ display: 'flex', justifyContent: 'center', alignItems: 'center', height: '80vh' }}>
        <CircularProgress />
      </Container>
    )
  }

  return (
    <Container maxWidth="lg" sx={{ py: 4 }}>
      <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', mb: 3 }}>
        <Typography variant="h4">Classrooms</Typography>
        <Button
          variant="contained"
          startIcon={<AddIcon />}
          onClick={() => setOpenDialog(true)}
        >
          New Classroom
        </Button>
      </Box>

      {error && <Alert severity="error" sx={{ mb: 3 }}>{error}</Alert>}

      <TableContainer component={Paper}>
        <Table>
          <TableHead>
            <TableRow sx={{ backgroundColor: '#f5f5f5' }}>
              <TableCell>Name</TableCell>
              <TableCell>Subject</TableCell>
              <TableCell>Semester</TableCell>
              <TableCell>Professor</TableCell>
              <TableCell align="right">Students</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {classrooms.length ? (
              classrooms.map((classroom) => (
                <TableRow key={classroom.id} hover>
                  <TableCell>{classroom.name}</TableCell>
                  <TableCell>{classroom.subject}</TableCell>
                  <TableCell>{classroom.semester}</TableCell>
                  <TableCell>{classroom.professorName || '-'}</TableCell>
                  <TableCell align="right">{classroom.studentCount || 0}</TableCell>
                </TableRow>
              ))
            ) : (
              <TableRow>
                <TableCell colSpan={5} align="center">
                  No classrooms available
                </TableCell>
              </TableRow>
            )}
          </TableBody>
        </Table>
      </TableContainer>

      {/* Create Dialog */}
      <Dialog open={openDialog} onClose={() => setOpenDialog(false)} maxWidth="sm" fullWidth>
        <Box sx={{ p: 3 }}>
          <Typography variant="h6" sx={{ mb: 2 }}>
            Create New Classroom
          </Typography>
          <TextField
            fullWidth
            label="Classroom Name"
            value={formData.name}
            onChange={(e) => setFormData({ ...formData, name: e.target.value })}
            margin="normal"
          />
          <TextField
            fullWidth
            label="Subject"
            value={formData.subject}
            onChange={(e) => setFormData({ ...formData, subject: e.target.value })}
            margin="normal"
          />
          <TextField
            fullWidth
            label="Semester"
            type="number"
            value={formData.semester}
            onChange={(e) => setFormData({ ...formData, semester: parseInt(e.target.value) })}
            margin="normal"
            select
          >
            {[1, 2, 3, 4, 5, 6, 7, 8].map((sem) => (
              <MenuItem key={sem} value={sem}>
                Semester {sem}
              </MenuItem>
            ))}
          </TextField>
          <Box sx={{ mt: 3, display: 'flex', gap: 1, justifyContent: 'flex-end' }}>
            <Button onClick={() => setOpenDialog(false)}>Cancel</Button>
            <Button variant="contained" onClick={handleCreate}>
              Create
            </Button>
          </Box>
        </Box>
      </Dialog>
    </Container>
  )
}


