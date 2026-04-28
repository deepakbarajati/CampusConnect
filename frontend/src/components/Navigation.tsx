import { AppBar, Toolbar, Typography, Button, Box, Menu, MenuItem } from '@mui/material'
import { Link as RouterLink, useNavigate } from 'react-router-dom'
import AccountCircleIcon from '@mui/icons-material/AccountCircle'
import { useState } from 'react'
import { useAuthStore } from '../store/authStore'

export default function Navigation() {
  const navigate = useNavigate()
  const { user, logout } = useAuthStore()
  const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null)

  const handleMenuOpen = (event: React.MouseEvent<HTMLElement>) => {
    setAnchorEl(event.currentTarget)
  }

  const handleMenuClose = () => {
    setAnchorEl(null)
  }

  const handleLogout = () => {
    logout()
    handleMenuClose()
    navigate('/login')
  }

  return (
    <AppBar position="static">
      <Toolbar>
        <Typography variant="h6" component={RouterLink} to="/dashboard" sx={{ flexGrow: 1, textDecoration: 'none', color: 'white' }}>
          CampusConnect
        </Typography>

        <Box sx={{ display: 'flex', gap: 2 }}>
          <Button color="inherit" component={RouterLink} to="/dashboard">
            Dashboard
          </Button>
          <Button color="inherit" component={RouterLink} to="/classroom">
            Classroom
          </Button>
          <Button color="inherit" component={RouterLink} to="/chat">
            Chat
          </Button>
          <Button color="inherit" component={RouterLink} to="/forum">
            Forum
          </Button>
          <Button color="inherit" component={RouterLink} to="/jobs">
            Jobs
          </Button>
          <Button color="inherit" component={RouterLink} to="/leaderboard">
            Leaderboard
          </Button>

          <Button
            color="inherit"
            startIcon={<AccountCircleIcon />}
            onClick={handleMenuOpen}
          >
            {user?.name || user?.email}
          </Button>
        </Box>

        <Menu
          anchorEl={anchorEl}
          open={Boolean(anchorEl)}
          onClose={handleMenuClose}
        >
          <MenuItem disabled>{user?.role}</MenuItem>
          <MenuItem onClick={handleLogout}>Logout</MenuItem>
        </Menu>
      </Toolbar>
    </AppBar>
  )
}

