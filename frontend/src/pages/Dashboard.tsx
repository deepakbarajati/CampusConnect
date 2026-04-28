import { useState, useEffect } from 'react'
import {
  Container,
  Paper,
  Box,
  Typography,
  Grid,
  Card,
  CardContent,
  Alert,
  CircularProgress,
} from '@mui/material'
import { useAuthStore } from '../store/authStore'
import { classroomService } from '../api/classroomService'
import { gamificationService } from '../api/gamificationService'
import { opportunityService } from '../api/opportunityService'

export default function Dashboard() {
  const { user } = useAuthStore()
  const [stats, setStats] = useState<any>(null)
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState('')

  useEffect(() => {
    const fetchStats = async () => {
      try {
        const [classroomsRes, pointsRes, opportunitiesRes] = await Promise.all([
          classroomService.getClassrooms(0, 5),
          gamificationService.getUserPoints(user?.id || ''),
          opportunityService.getActiveOpportunities(),
        ])

        setStats({
          classrooms: classroomsRes.data,
          points: pointsRes.data.points,
          opportunities: opportunitiesRes.data,
        })
      } catch (err: any) {
        setError('Failed to load dashboard data')
        console.error(err)
      } finally {
        setLoading(false)
      }
    }

    if (user?.id) {
      fetchStats()
    }
  }, [user?.id])

  if (loading) {
    return (
      <Container sx={{ display: 'flex', justifyContent: 'center', alignItems: 'center', height: '80vh' }}>
        <CircularProgress />
      </Container>
    )
  }

  return (
    <Container maxWidth="lg" sx={{ py: 4 }}>
      {error && <Alert severity="error" sx={{ mb: 3 }}>{error}</Alert>}

      <Typography variant="h4" sx={{ mb: 4 }}>
        Welcome, {user?.name || user?.email}!
      </Typography>

      <Grid container spacing={3}>
        {/* Stats Cards */}
        <Grid item xs={12} sm={6} md={3}>
          <Card>
            <CardContent>
              <Typography color="textSecondary" gutterBottom>
                XP Points
              </Typography>
              <Typography variant="h5">
                {stats?.points || 0}
              </Typography>
            </CardContent>
          </Card>
        </Grid>

        <Grid item xs={12} sm={6} md={3}>
          <Card>
            <CardContent>
              <Typography color="textSecondary" gutterBottom>
                Classrooms
              </Typography>
              <Typography variant="h5">
                {stats?.classrooms?.length || 0}
              </Typography>
            </CardContent>
          </Card>
        </Grid>

        <Grid item xs={12} sm={6} md={3}>
          <Card>
            <CardContent>
              <Typography color="textSecondary" gutterBottom>
                Job Opportunities
              </Typography>
              <Typography variant="h5">
                {stats?.opportunities?.length || 0}
              </Typography>
            </CardContent>
          </Card>
        </Grid>

        <Grid item xs={12} sm={6} md={3}>
          <Card>
            <CardContent>
              <Typography color="textSecondary" gutterBottom>
                Role
              </Typography>
              <Typography variant="h5">
                {user?.role}
              </Typography>
            </CardContent>
          </Card>
        </Grid>

        {/* Recent Classrooms */}
        <Grid item xs={12}>
          <Paper sx={{ p: 3 }}>
            <Typography variant="h6" sx={{ mb: 2 }}>
              Recent Classrooms
            </Typography>
            {stats?.classrooms?.length ? (
              <Box>
                {stats.classrooms.map((classroom: any) => (
                  <Box key={classroom.id} sx={{ mb: 1, p: 1, border: '1px solid #ddd', borderRadius: 1 }}>
                    <Typography variant="subtitle1">{classroom.name}</Typography>
                    <Typography variant="body2" color="textSecondary">
                      {classroom.subject} - Semester {classroom.semester}
                    </Typography>
                  </Box>
                ))}
              </Box>
            ) : (
              <Typography color="textSecondary">No classrooms yet</Typography>
            )}
          </Paper>
        </Grid>
      </Grid>
    </Container>
  )
}

