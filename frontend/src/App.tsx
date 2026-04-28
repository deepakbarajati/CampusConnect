import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom'
import { ThemeProvider, createTheme } from '@mui/material/styles'
import CssBaseline from '@mui/material/CssBaseline'
import Login from './pages/Login'
import Register from './pages/Register'
import Dashboard from './pages/Dashboard'
import Classroom from './pages/Classroom'
import Chat from './pages/Chat'
import Forum from './pages/Forum'
import Jobs from './pages/Jobs'
import Leaderboard from './pages/Leaderboard'
import ProtectedRoute from './components/ProtectedRoute'
import Navigation from './components/Navigation'
import { useAuthStore } from './store/authStore'

const theme = createTheme({
  palette: {
    primary: {
      main: '#1976d2',
    },
    secondary: {
      main: '#dc004e',
    },
  },
  typography: {
    fontFamily: '"Roboto", "Helvetica", "Arial", sans-serif',
  },
})

function App() {
  const { isAuthenticated } = useAuthStore()

  return (
    <ThemeProvider theme={theme}>
      <CssBaseline />
      <Router>
        {isAuthenticated && <Navigation />}
        <Routes>
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />

          {/* Protected Routes */}
          <Route element={<ProtectedRoute />}>
            <Route path="/dashboard" element={<Dashboard />} />
            <Route path="/classroom" element={<Classroom />} />
            <Route path="/chat" element={<Chat />} />
            <Route path="/forum" element={<Forum />} />
            <Route path="/jobs" element={<Jobs />} />
            <Route path="/leaderboard" element={<Leaderboard />} />
          </Route>

          <Route path="/" element={isAuthenticated ? <Navigate to="/dashboard" /> : <Navigate to="/login" />} />
          <Route path="*" element={<Navigate to="/" />} />
        </Routes>
      </Router>
    </ThemeProvider>
  )
}

export default App

