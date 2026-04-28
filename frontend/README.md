# React + TypeScript + Vite Frontend for CampusConnect

Modern React frontend for the CampusConnect microservices platform, built with TypeScript and Vite.

## 🚀 Features

- **Authentication System** - Secure login/register with JWT tokens
- **Classroom Management** - View and manage academic classrooms
- **Real-time Chat** - Messaging system integrated with backend
- **Forum** - Discussion boards for students and faculty
- **Job Opportunities** - Browse and apply for jobs
- **Leaderboard** - Gamification and user rankings
- **Dashboard** - Quick overview of all features

## 🛠️ Tech Stack

- **React 18** - UI library
- **TypeScript** - Type safety
- **Vite** - Build tool
- **Material-UI (MUI)** - Component library
- **React Router v6** - Routing
- **Axios** - HTTP client
- **Zustand** - State management
- **js-cookie** - Cookie management
- **JWT Decode** - Token parsing

## 📦 Installation

```bash
cd frontend
npm install
```

## 🚀 Development

```bash
npm run dev
```

The app will run at `http://localhost:3000` and proxy API requests to `http://localhost:8080/api`.

## 📦 Build

```bash
npm run build
```

Output will be in the `dist/` directory.

## 🏗️ Project Structure

```
src/
├── api/                 # API service clients
│   ├── client.ts       # Axios instance with interceptors
│   ├── authService.ts  # Authentication endpoints
│   ├── classroomService.ts
│   ├── chatService.ts
│   ├── forumService.ts
│   ├── opportunityService.ts
│   └── gamificationService.ts
├── pages/              # Page components
│   ├── Login.tsx
│   ├── Register.tsx
│   ├── Dashboard.tsx
│   ├── Classroom.tsx
│   ├── Chat.tsx
│   ├── Forum.tsx
│   ├── Jobs.tsx
│   └── Leaderboard.tsx
├── components/         # Reusable components
│   ├── Navigation.tsx
│   └── ProtectedRoute.tsx
├── store/             # Zustand stores
│   └── authStore.ts
├── types/             # TypeScript definitions
├── App.tsx            # Main app component
├── main.tsx           # Entry point
└── index.css          # Global styles
```

## 🔐 Authentication

- Tokens are stored in cookies (httpOnly recommended in production)
- JWT tokens automatically included in Authorization headers
- Protected routes redirect unauthenticated users to login
- Token expiration handled with 401 response

## 🌐 API Routes

All API requests go through the backend API Gateway at `http://localhost:8080/api`:

- `/auth/*` - Authentication
- `/classroom/*` - Classroom management
- `/chat/*` - Chat/messaging
- `/forum/*` - Forum/discussions
- `/opportunity/*` - Job opportunities
- `/gamification/*` - Leaderboard and points

## 🔌 Environment Variables

Create a `.env` file:

```env
VITE_API_URL=http://localhost:8080/api
```

## 📝 Available Scripts

- `npm run dev` - Start development server
- `npm run build` - Build for production
- `npm run preview` - Preview production build
- `npm run lint` - Run ESLint
- `npm run type-check` - Check TypeScript types

## 🚀 Production Build

```bash
npm run build
npm run preview
```

## 📦 Deployment

The built `dist/` folder can be served by any static file server:

```bash
# Using a simple HTTP server
npx serve -s dist

# Or with Nginx
# Copy dist/* to /var/www/html
```

## 🤝 Contributing

1. Create a feature branch
2. Make your changes
3. Test thoroughly
4. Submit a pull request

## 📄 License

MIT

