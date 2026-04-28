import { useState, useEffect } from 'react'
import {
  Container,
  Box,
  Typography,
  Button,
  Dialog,
  TextField,
  Card,
  CardContent,
  CardActions,
  Alert,
  CircularProgress,
} from '@mui/material'
import AddIcon from '@mui/icons-material/Add'
import { forumService, ForumDTO } from '../api/forumService'

export default function Forum() {
  const [forums, setForums] = useState<ForumDTO[]>([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState('')
  const [openDialog, setOpenDialog] = useState(false)
  const [formData, setFormData] = useState({
    title: '',
    content: '',
    category: 'General',
  })

  useEffect(() => {
    fetchForums()
  }, [])

  const fetchForums = async () => {
    try {
      const response = await forumService.getForums(0, 20)
      setForums(response.data)
      setError('')
    } catch (err: any) {
      setError('Failed to load forums')
      console.error(err)
    } finally {
      setLoading(false)
    }
  }

  const handleCreate = async () => {
    try {
      await forumService.createForum({
        ...formData,
        userId: '',
      })
      setOpenDialog(false)
      setFormData({ title: '', content: '', category: 'General' })
      await fetchForums()
    } catch (err: any) {
      setError('Failed to create forum post')
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
        <Typography variant="h4">Forum</Typography>
        <Button
          variant="contained"
          startIcon={<AddIcon />}
          onClick={() => setOpenDialog(true)}
        >
          New Post
        </Button>
      </Box>

      {error && <Alert severity="error" sx={{ mb: 3 }}>{error}</Alert>}

      <Box sx={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fill, minmax(300px, 1fr))', gap: 2 }}>
        {forums.length ? (
          forums.map((forum) => (
            <Card key={forum.id}>
              <CardContent>
                <Typography variant="h6" gutterBottom>
                  {forum.title}
                </Typography>
                <Typography variant="body2" color="textSecondary" sx={{ mb: 2 }}>
                  {forum.content.substring(0, 100)}...
                </Typography>
                <Typography variant="caption" color="textSecondary">
                  By {forum.userName || 'Anonymous'} • {forum.repliesCount || 0} replies
                </Typography>
              </CardContent>
              <CardActions>
                <Button size="small">View</Button>
                <Button size="small">Reply</Button>
              </CardActions>
            </Card>
          ))
        ) : (
          <Typography color="textSecondary" align="center" sx={{ gridColumn: '1/-1', mt: 4 }}>
            No forum posts yet
          </Typography>
        )}
      </Box>

      {/* Create Dialog */}
      <Dialog open={openDialog} onClose={() => setOpenDialog(false)} maxWidth="sm" fullWidth>
        <Box sx={{ p: 3 }}>
          <Typography variant="h6" sx={{ mb: 2 }}>
            Create New Forum Post
          </Typography>
          <TextField
            fullWidth
            label="Title"
            value={formData.title}
            onChange={(e) => setFormData({ ...formData, title: e.target.value })}
            margin="normal"
          />
          <TextField
            fullWidth
            label="Content"
            value={formData.content}
            onChange={(e) => setFormData({ ...formData, content: e.target.value })}
            margin="normal"
            multiline
            rows={4}
          />
          <TextField
            fullWidth
            label="Category"
            value={formData.category}
            onChange={(e) => setFormData({ ...formData, category: e.target.value })}
            margin="normal"
          />
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




