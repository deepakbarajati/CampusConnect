import { useState, useEffect } from 'react'
import {
  Container,
  Paper,
  Box,
  Typography,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Alert,
  CircularProgress,
  Chip,
} from '@mui/material'
import EmojiEventsIcon from '@mui/icons-material/EmojiEvents'
import { gamificationService, LeaderboardDTO } from '../api/gamificationService'

export default function Leaderboard() {
  const [leaderboard, setLeaderboard] = useState<LeaderboardDTO[]>([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState('')

  useEffect(() => {
    fetchLeaderboard()
  }, [])

  const fetchLeaderboard = async () => {
    try {
      const response = await gamificationService.getLeaderboard(undefined, 100)
      setLeaderboard(response.data)
      setError('')
    } catch (err: any) {
      setError('Failed to load leaderboard')
      console.error(err)
    } finally {
      setLoading(false)
    }
  }

  const getMedalColor = (rank: number) => {
    if (rank === 1) return '#FFD700'
    if (rank === 2) return '#C0C0C0'
    if (rank === 3) return '#CD7F32'
    return undefined
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
      <Box sx={{ display: 'flex', gap: 1, alignItems: 'center', mb: 3 }}>
        <EmojiEventsIcon fontSize="large" color="primary" />
        <Typography variant="h4">Leaderboard</Typography>
      </Box>

      {error && <Alert severity="error" sx={{ mb: 3 }}>{error}</Alert>}

      <TableContainer component={Paper}>
        <Table>
          <TableHead>
            <TableRow sx={{ backgroundColor: '#f5f5f5' }}>
              <TableCell align="center" width="10%">
                Rank
              </TableCell>
              <TableCell>Name</TableCell>
              <TableCell align="center" width="15%">
                Level
              </TableCell>
              <TableCell align="right" width="20%">
                XP Points
              </TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {leaderboard.length ? (
              leaderboard.map((entry, index) => (
                <TableRow
                  key={entry.userId}
                  sx={{
                    backgroundColor: index < 3 ? `${getMedalColor(entry.rank)}20` : 'transparent',
                    '&:hover': { backgroundColor: '#f9f9f9' },
                  }}
                >
                  <TableCell align="center">
                    {entry.rank === 1 && '🥇'}
                    {entry.rank === 2 && '🥈'}
                    {entry.rank === 3 && '🥉'}
                    {entry.rank > 3 && entry.rank}
                  </TableCell>
                  <TableCell>
                    <Typography variant="body2" sx={{ fontWeight: entry.rank <= 3 ? 600 : 400 }}>
                      {entry.userName}
                    </Typography>
                  </TableCell>
                  <TableCell align="center">
                    <Chip label={`Level ${entry.level}`} size="small" variant="outlined" />
                  </TableCell>
                  <TableCell align="right">
                    <Typography variant="body2" sx={{ fontWeight: 600 }}>
                      {entry.points.toLocaleString()}
                    </Typography>
                  </TableCell>
                </TableRow>
              ))
            ) : (
              <TableRow>
                <TableCell colSpan={4} align="center">
                  No leaderboard data available
                </TableCell>
              </TableRow>
            )}
          </TableBody>
        </Table>
      </TableContainer>
    </Container>
  )
}

