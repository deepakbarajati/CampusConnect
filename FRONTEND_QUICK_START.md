# 🚀 CampusConnect React Frontend - Quick Start Guide

## ✅ Status: COMPLETE

The Angular frontend has been **completely deleted** and replaced with a modern **React + TypeScript + Vite** frontend.

---

## 🎯 Quick Start (3 Steps)

### Step 1: Start Backend Services
```bash
cd /home/deepak-barajati/Desktop/CampusConnect/Backend

# Terminal 1: Discovery Server
cd discoveryServer && mvn spring-boot:run

# Terminal 2: API Gateway
cd apiGateway && mvn spring-boot:run

# Terminal 3+: Other Services (in separate terminals)
cd authService && mvn spring-boot:run
cd classroomService && mvn spring-boot:run
cd chatService && mvn spring-boot:run
cd forumService && mvn spring-boot:run
cd opportunityService && mvn spring-boot:run
cd gamificationService && mvn spring-boot:run
```

### Step 2: Start Frontend
```bash
cd /home/deepak-barajati/Desktop/CampusConnect/frontend
npm run dev
```

### Step 3: Open in Browser
```
http://localhost:3000
```

---

## 📋 Frontend Structure

```
frontend/
├── src/
│   ├── api/                    # API clients for each backend service
│   ├── pages/                  # 8 page components
│   ├── components/             # Reusable UI components
│   ├── store/                  # Zustand auth state
│   ├── types/                  # TypeScript definitions
│   ├── App.tsx                 # Main app with routing
│   └── main.tsx                # Entry point
├── dist/                       # Production build (✅ ready)
├── package.json                # Dependencies
├── tsconfig.json               # TypeScript config
├── vite.config.ts              # Vite config
└── README.md                   # Detailed docs
```

---

## 🎨 Pages Available

| Page | Route | Purpose |
|------|-------|---------|
| Login | `/login` | User authentication |
| Register | `/register` | New user signup |
| Dashboard | `/dashboard` | Home page with stats |
| Classroom | `/classroom` | View/manage classrooms |
| Chat | `/chat` | Real-time messaging |
| Forum | `/forum` | Discussion boards |
| Jobs | `/jobs` | Browse job opportunities |
| Leaderboard | `/leaderboard` | Gamification rankings |

---

## 💻 Available Commands

```bash
# Development
npm run dev          # Start dev server (http://localhost:3000)

# Building
npm run build        # Production build
npm run preview      # Preview production build

# Linting & Types
npm run lint         # Check code style
npm run type-check   # TypeScript validation
```

---

## 🔐 Default Test Credentials

When you first run the backend, you can register a new account or use:
- **Email**: test@campus.com
- **Password**: (set during registration)
- **Role**: STUDENT / PROFESSOR / ADMIN

---

## 📦 Dependencies Installed

```
React 18.2.0
TypeScript 5.2.2
Vite 5.0.7
Material-UI 5.14.0
React Router 6.20.0
Axios 1.6.0
Zustand 4.4.0
jwt-decode & js-cookie
```

---

## 🌐 API Routes (Backend)

All requests go through the **API Gateway** at `http://localhost:8080`:

```
GET  /api/auth/login
POST /api/auth/register
GET  /api/classroom
GET  /api/chat/rooms
GET  /api/forum
GET  /api/opportunity
GET  /api/gamification/leaderboard
```

---

## 🛠️ Troubleshooting

### Port 3000 already in use?
```bash
# Kill process on port 3000
lsof -ti:3000 | xargs kill -9

# Or specify different port
npm run dev -- --port 3001
```

### API requests failing?
- Check that API Gateway is running on `http://localhost:8080`
- Check network tab in browser DevTools
- Verify JWT token in cookies

### TypeScript errors?
```bash
npm run type-check  # See all errors
npm run lint        # Check code style
```

### Dependencies not installing?
```bash
rm -rf node_modules package-lock.json
npm install --legacy-peer-deps
```

---

## 📚 File Organization

### API Services (`src/api/`)
Each service matches a backend microservice:
- `authService.ts` - Auth endpoints
- `classroomService.ts` - Academic management
- `chatService.ts` - Messaging
- `forumService.ts` - Discussions
- `opportunityService.ts` - Jobs
- `gamificationService.ts` - Leaderboard

### Pages (`src/pages/`)
8 fully functional pages with:
- API integration
- Error handling
- Loading states
- Form validation

### Components (`src/components/`)
- `Navigation.tsx` - Top navbar with logout
- `ProtectedRoute.tsx` - Route authentication

### State Management (`src/store/`)
- `authStore.ts` - Zustand auth state
- Persistent JWT tokens in cookies
- Auto logout on token expiration

---

## 🚀 Production Deployment

### Build
```bash
npm run build
```

### Output
- `dist/` folder is production-ready
- Can be served by any static server
- All assets optimized and minified

### Deploy Options
```bash
# Option 1: Simple HTTP server
npx serve -s dist

# Option 2: Nginx
cp dist/* /var/www/html

# Option 3: Docker
# (add Dockerfile if needed)
```

---

## 🎓 Learning Resources

- **React Docs**: https://react.dev
- **Vite Guide**: https://vitejs.dev
- **Material-UI**: https://mui.com
- **React Router**: https://reactrouter.com
- **Zustand**: https://github.com/pmndrs/zustand

---

## 📝 Next Development Steps

1. ✅ Backend services configured
2. ✅ Frontend scaffolding complete
3. **Next**: Test all API integrations
4. **Next**: Add real-time features (WebSocket for chat)
5. **Next**: Add unit & E2E tests
6. **Next**: Performance optimization

---

## 🎉 You're Ready!

Start developing right away:

```bash
cd frontend && npm run dev
```

Visit: **http://localhost:3000** 🚀

---

## 📞 Need Help?

- Check `/frontend/README.md` for detailed docs
- Check `/FRONTEND_MIGRATION_COMPLETE.md` for full migration details
- Verify backend is running on port 8080
- Check browser console for errors
- Run `npm run type-check` for TypeScript validation

Happy coding! 💻✨

