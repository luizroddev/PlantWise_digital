version: '3'
services:
  app:
    build: .
    ports:
      - "080:8080"
    volumes:
      - "app-data:/app/data"
volumes:
  "app-data":
