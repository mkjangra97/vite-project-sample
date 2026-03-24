# ─────────────────────────────────────────────
# Stage 1: Build the Vite React app
# ─────────────────────────────────────────────
FROM node:20-alpine AS builder

WORKDIR /app

# Copy dependency files first for better layer caching
COPY package*.json ./

RUN npm ci

# Copy source code
COPY . .

# Build for production
RUN npm run build

# ─────────────────────────────────────────────
# Stage 2: Serve with Node (no nginx)
# ─────────────────────────────────────────────
FROM node:20-alpine AS production

# Install 'serve' to serve static files
RUN npm install -g serve

WORKDIR /app

# Copy built assets from builder stage
COPY --from=builder /app/dist ./dist

EXPOSE 4174

CMD ["serve", "-s", "dist", "-l", "4173"]
