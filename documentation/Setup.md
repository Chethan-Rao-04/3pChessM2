# Setup Guide

## Prerequisites
- Java 17 or higher
- Gradle 7.x or higher
- Git
- Docker (optional)
- Kubernetes (optional)

## Development Environment Setup

### 1. Clone the Repository
```bash
git clone https://github.com/yourusername/3pChessM2.git
cd 3pChessM2
```

### 2. Project Structure
The project is organized as a single Spring Boot application with integrated frontend:
```
app/
├── src/
│   ├── main/
│   │   ├── java/        # Backend source code
│   │   └── resources/
│   │       └── static/  # Frontend assets
│   └── test/
│       └── java/        # Test files
└── build.gradle
```

### 3. Build and Run
```bash
# Build the project
./gradlew clean build

# Run the application
./gradlew bootRun
```

The application will be available at `http://localhost:8080`

### 4. Development Workflow
1. Backend changes:
   - Edit Java files in `app/src/main/java`
   - Application will auto-reload on changes

2. Frontend changes:
   - Edit files in `app/src/main/resources/static`
   - Refresh browser to see changes
   - For hot-reload, use browser dev tools

## Local Development Environment

### Prerequisites
1. Development Tools
   - JDK 17
   - Gradle 7.x
   - Git
   - IDE (IntelliJ IDEA recommended)
   - Node.js and npm (for frontend development)

2. System Requirements
   - 4GB RAM minimum
   - 2GB free disk space
   - Modern web browser
   - Network connectivity

### Initial Setup

#### 1. Repository Setup
```bash
# Clone the repository
git clone https://github.com/yourusername/3pChessM2.git
cd 3pChessM2

# Initialize git submodules (if any)
git submodule update --init --recursive
```

#### 2. app Setup
```bash
# Build the app
cd app
./gradlew clean build

# Run tests
./gradlew test

# Start the development server
./gradlew bootRun
```


```

### IDE Configuration

#### IntelliJ IDEA Setup
1. Import Project
   - Open IntelliJ IDEA
   - File → Open
   - Select the project root directory
   - Import as Gradle project

2. Configure JDK
   - File → Project Structure
   - Project Settings → Project
   - Set JDK to version 17

3. Run Configurations
   - Add Spring Boot configuration
   - Main class: `com.ccd.chess.SpringApplication`
   - Working directory: `$PROJECT_DIR$`
   - Use classpath of module: `app`

#### VS Code Setup
1. Extensions
   - Java Extension Pack
   - Spring Boot Extension Pack
   - Gradle Extension Pack

2. Settings
   - Java Home: JDK 17 path
   - Gradle: Use Wrapper

## Production Deployment

### Build Process

#### 1. app Build
```bash
# Create production build
./gradlew clean build -Pprod

# Generate documentation
./gradlew javadoc
```



### Deployment Options

#### 1. Docker Deployment
```bash
# Build Docker image
docker build -t 3pchess .

# Run container
docker run -p 8080:8080 3pchess
```

#### 2. Traditional Deployment
```bash
# Copy JAR file
scp build/libs/3pchess.jar user@server:/app/

# Run application
java -jar 3pchess.jar --spring.profiles.active=prod
```

### Server Requirements

#### Minimum Specifications
- 2 CPU cores
- 4GB RAM
- 10GB storage
- Java 17 runtime
- Network access

#### Recommended Specifications
- 4 CPU cores
- 8GB RAM
- 20GB SSD storage
- Java 17 runtime
- Load balancer

### Configuration

#### 1. Environment Variables
```bash
# Database configuration
DB_HOST=localhost
DB_PORT=5432
DB_NAME=chess
DB_USER=admin
DB_PASSWORD=secure_password

# Application settings
SERVER_PORT=8080
SPRING_PROFILES_ACTIVE=prod
```

#### 2. Application Properties
```properties
# application-prod.properties
spring.datasource.url=jdbc:postgresql://${DB_HOST}:${DB_PORT}/${DB_NAME}
spring.datasource.username=${DB_USER}
spring.datasource.password=${DB_PASSWORD}

# Logging
logging.level.root=INFO
logging.file.name=/var/log/3pchess/application.log
```

### Monitoring

#### 1. Health Checks
- `/actuator/health`: System health
- `/actuator/metrics`: Application metrics
- `/actuator/info`: Application info

#### 2. Logging
- Application logs: `/var/log/3pchess/`
- Access logs: `/var/log/nginx/`
- Error logs: `/var/log/3pchess/error.log`

### Backup and Recovery

#### 1. Database Backup
```bash
# Backup database
pg_dump -U ${DB_USER} -F c -b -v -f backup.dump ${DB_NAME}

# Restore database
pg_restore -U ${DB_USER} -d ${DB_NAME} backup.dump
```

#### 2. Application Backup
```bash
# Backup configuration
cp -r /etc/3pchess/config/* /backup/config/

# Backup logs
cp -r /var/log/3pchess/* /backup/logs/
```

### Security Considerations

#### 1. Application Security
- Enable HTTPS
- Configure CORS
- Set secure headers
- Enable CSRF protection

#### 2. Server Security
- Firewall configuration
- Regular updates
- Security monitoring
- Access control

### Troubleshooting

#### Common Issues
1. Application won't start
   - Check Java version
   - Verify configuration
   - Check logs

2. Performance issues
   - Monitor resources
   - Check database connections
   - Review logging levels

3. Connection issues
   - Verify network settings
   - Check firewall rules
   - Validate SSL certificates

### Maintenance

#### 1. Regular Tasks
- Log rotation
- Database optimization
- Security updates
- Performance monitoring

#### 2. Update Process
```bash
# Pull latest changes
git pull origin main

# Build application
./gradlew clean build

# Deploy new version
./deploy.sh
```

### Scaling

#### 1. Horizontal Scaling
- Load balancer configuration
- Session management
- Database replication
- Cache synchronization

#### 2. Vertical Scaling
- Resource allocation
- JVM tuning
- Database optimization
- Cache configuration 