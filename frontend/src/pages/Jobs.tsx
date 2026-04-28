import { useState, useEffect } from 'react'
import {
  Container,
  Box,
  Typography,
  Button,
  Card,
  CardContent,
  CardActions,
  Alert,
  CircularProgress,
  Chip,
} from '@mui/material'
import WorkIcon from '@mui/icons-material/Work'
import { opportunityService, OpportunityDTO } from '../api/opportunityService'

export default function Jobs() {
  const [opportunities, setOpportunities] = useState<OpportunityDTO[]>([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState('')
  const [applied, setApplied] = useState<Set<string>>(new Set())

  useEffect(() => {
    fetchOpportunities()
  }, [])

  const fetchOpportunities = async () => {
    try {
      const response = await opportunityService.getActiveOpportunities()
      setOpportunities(response.data)
      setError('')
    } catch (err: any) {
      setError('Failed to load opportunities')
      console.error(err)
    } finally {
      setLoading(false)
    }
  }

  const handleApply = async (opportunityId: string) => {
    try {
      await opportunityService.applyForOpportunity(opportunityId)
      setApplied((prev) => new Set([...prev, opportunityId]))
    } catch (err: any) {
      setError('Failed to apply for opportunity')
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
      <Box sx={{ display: 'flex', gap: 1, alignItems: 'center', mb: 3 }}>
        <WorkIcon fontSize="large" />
        <Typography variant="h4">Job Opportunities</Typography>
      </Box>

      {error && <Alert severity="error" sx={{ mb: 3 }}>{error}</Alert>}

      <Box sx={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fill, minmax(300px, 1fr))', gap: 2 }}>
        {opportunities.length ? (
          opportunities.map((opportunity) => (
            <Card key={opportunity.id}>
              <CardContent>
                <Typography variant="h6" gutterBottom>
                  {opportunity.position}
                </Typography>
                <Typography variant="body2" color="textSecondary" gutterBottom>
                  {opportunity.company}
                </Typography>
                <Box sx={{ my: 1 }}>
                  {opportunity.location && (
                    <Chip label={opportunity.location} size="small" sx={{ mr: 1 }} />
                  )}
                  {opportunity.salary && (
                    <Chip label={`₹${opportunity.salary}`} size="small" />
                  )}
                </Box>
                <Typography variant="body2" sx={{ mt: 2 }}>
                  {opportunity.description.substring(0, 150)}...
                </Typography>
              </CardContent>
              <CardActions>
                <Button
                  size="small"
                  onClick={() => handleApply(opportunity.id)}
                  disabled={applied.has(opportunity.id)}
                >
                  {applied.has(opportunity.id) ? 'Applied' : 'Apply'}
                </Button>
              </CardActions>
            </Card>
          ))
        ) : (
          <Typography color="textSecondary" align="center" sx={{ gridColumn: '1/-1', mt: 4 }}>
            No job opportunities available
          </Typography>
        )}
      </Box>
    </Container>
  )
}


