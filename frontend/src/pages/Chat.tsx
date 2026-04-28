import { useState, useEffect } from 'react'
import {
  Container,
  Paper,
  Box,
  Typography,
  TextField,
  Button,
  List,
  ListItemButton,
  ListItemText,
  Divider,
  Alert,
  CircularProgress,
} from '@mui/material'
import SendIcon from '@mui/icons-material/Send'
import { chatService, MessageDTO, ChatRoomDTO } from '../api/chatService'

export default function Chat() {
  const [rooms, setRooms] = useState<ChatRoomDTO[]>([])
  const [selectedRoom, setSelectedRoom] = useState<ChatRoomDTO | null>(null)
  const [messages, setMessages] = useState<MessageDTO[]>([])
  const [newMessage, setNewMessage] = useState('')
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState('')

  useEffect(() => {
    fetchRooms()
  }, [])

  const fetchRooms = async () => {
    try {
      const response = await chatService.getRooms()
      setRooms(response.data)
      setError('')
    } catch (err: any) {
      setError('Failed to load chat rooms')
      console.error(err)
    } finally {
      setLoading(false)
    }
  }

  const handleSelectRoom = async (room: ChatRoomDTO) => {
    setSelectedRoom(room)
    try {
      const response = await chatService.getMessages(room.id)
      setMessages(response.data)
    } catch (err: any) {
      setError('Failed to load messages')
    }
  }

  const handleSendMessage = async () => {
    if (!newMessage.trim() || !selectedRoom) return

    try {
      const response = await chatService.sendMessage(selectedRoom.id, newMessage)
      setMessages([...messages, response.data])
      setNewMessage('')
    } catch (err: any) {
      setError('Failed to send message')
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
    <Container maxWidth="lg" sx={{ py: 4, height: '80vh', display: 'flex', flexDirection: 'column' }}>
      <Typography variant="h4" sx={{ mb: 3 }}>
        Chat
      </Typography>

      {error && <Alert severity="error" sx={{ mb: 2 }}>{error}</Alert>}

      <Box sx={{ display: 'flex', gap: 2, flex: 1 }}>
        {/* Rooms List */}
        <Paper sx={{ width: '25%', overflowY: 'auto', p: 2 }}>
          <Typography variant="h6" sx={{ mb: 2 }}>
            Chat Rooms
          </Typography>
          <List>
            {rooms.length ? (
              rooms.map((room) => (
                <Box key={room.id}>
                  <ListItemButton
                    selected={selectedRoom?.id === room.id}
                    onClick={() => handleSelectRoom(room)}
                  >
                    <ListItemText
                      primary={room.name}
                      secondary={room.type}
                    />
                  </ListItemButton>
                  <Divider />
                </Box>
              ))
            ) : (
              <Typography color="textSecondary" align="center" sx={{ mt: 2 }}>
                No chat rooms
              </Typography>
            )}
          </List>
        </Paper>

        {/* Messages */}
        <Paper sx={{ flex: 1, display: 'flex', flexDirection: 'column', overflowY: 'hidden' }}>
          {selectedRoom ? (
            <>
              <Box sx={{ p: 2, borderBottom: '1px solid #ddd' }}>
                <Typography variant="h6">{selectedRoom.name}</Typography>
              </Box>

              {/* Messages List */}
              <Box sx={{ flex: 1, overflowY: 'auto', p: 2 }}>
                {messages.length ? (
                  messages.map((msg) => (
                    <Box key={msg.id} sx={{ mb: 2 }}>
                      <Typography variant="body2" color="textSecondary">
                        {new Date(msg.sentAt).toLocaleTimeString()}
                      </Typography>
                      <Typography variant="body1" sx={{ mt: 0.5 }}>
                        {msg.content}
                      </Typography>
                    </Box>
                  ))
                ) : (
                  <Typography color="textSecondary" align="center">
                    No messages yet
                  </Typography>
                )}
              </Box>

              {/* Message Input */}
              <Box sx={{ p: 2, borderTop: '1px solid #ddd', display: 'flex', gap: 1 }}>
                <TextField
                  fullWidth
                  size="small"
                  placeholder="Type a message..."
                  value={newMessage}
                  onChange={(e) => setNewMessage(e.target.value)}
                  onKeyPress={(e) => e.key === 'Enter' && handleSendMessage()}
                />
                <Button
                  variant="contained"
                  endIcon={<SendIcon />}
                  onClick={handleSendMessage}
                >
                  Send
                </Button>
              </Box>
            </>
          ) : (
            <Box sx={{ display: 'flex', justifyContent: 'center', alignItems: 'center', height: '100%' }}>
              <Typography color="textSecondary">Select a chat room to begin</Typography>
            </Box>
          )}
        </Paper>
      </Box>
    </Container>
  )
}
