# Base image
FROM node:22-alpine

WORKDIR /home/gradle

# Copy dependency files
COPY package.json package-lock.json ./

# Install depdendencies
RUN npm ci

# Copy source code
COPY . .

# Build static files
RUN npm run build

EXPOSE 4173

CMD ["npm", "run", "preview", "--", "--host"]