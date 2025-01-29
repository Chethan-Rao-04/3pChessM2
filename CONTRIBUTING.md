# Contributing to Three Player Chess

Thank you for your interest in contributing to Three Player Chess! This document provides guidelines and instructions for contributing to the project.

## Code of Conduct

This project adheres to a Code of Conduct that all contributors are expected to follow. Please read [CODE_OF_CONDUCT.md](CODE_OF_CONDUCT.md) before contributing.

## How to Contribute

### Reporting Bugs
1. Check if the bug has already been reported in the Issues section
2. If not, create a new issue with the "bug" label
3. Include:
   - Clear description of the problem
   - Steps to reproduce
   - Expected vs actual behavior
   - System information
   - Screenshots if applicable

### Suggesting Enhancements
1. Check existing issues for similar suggestions
2. Create a new issue with the "enhancement" label
3. Provide:
   - Clear description of the feature
   - Use cases and benefits
   - Potential implementation approach
   - Mock-ups or examples if applicable

### Pull Requests

#### Getting Started
1. Fork the repository
2. Clone your fork:
   ```bash
   git clone https://github.com/your-username/3pChessM2.git
   ```
3. Add upstream remote:
   ```bash
   git remote add upstream https://github.com/original-owner/3pChessM2.git
   ```
4. Create a feature branch:
   ```bash
   git checkout -b feature/your-feature-name
   ```

#### Development Process
1. Follow coding standards in [Development.md](documentation/Development.md)
2. Write tests following [Testing.md](documentation/Testing.md)
3. Update documentation as needed
4. Commit changes:
   ```bash
   git commit -m "feat: add your feature description"
   ```

#### Commit Message Guidelines
Format: `type(scope): description`

Types:
- feat: New feature
- fix: Bug fix
- docs: Documentation changes
- style: Code style changes
- refactor: Code refactoring
- test: Test changes
- chore: Build/maintenance changes

Example:
```
feat(board): add diagonal movement for bishops
```

#### Submitting Changes
1. Push to your fork:
   ```bash
   git push origin feature/your-feature-name
   ```
2. Create pull request
3. Fill out PR template
4. Wait for review

### Development Setup
1. Follow setup instructions in [Setup.md](documentation/Setup.md)
2. Install development tools:
   - JDK 17
   - Gradle
   - Node.js
   - Git

### Testing
1. Run backend tests:
   ```bash
   ./gradlew test
   ```
2. Run frontend tests:
   ```bash
   npm test
   ```
3. Ensure all tests pass
4. Maintain or improve coverage

## Style Guidelines

### Java Code
- Follow Google Java Style Guide
- Use meaningful variable names
- Document public APIs
- Keep methods focused
- Write unit tests

### JavaScript Code
- Use ES6+ features
- Follow Airbnb Style Guide
- Use meaningful variable names
- Document complex logic
- Write unit tests

### Documentation
- Clear and concise
- Examples where helpful
- Proper formatting
- Keep up to date

## Review Process

### Pull Request Reviews
1. Code quality check
2. Test coverage verification
3. Documentation review
4. Performance impact
5. Security considerations

### Approval Requirements
- At least one maintainer approval
- All tests passing
- Documentation updated
- Style guidelines followed

## Community

### Communication Channels
- GitHub Issues
- Project Discord
- Development mailing list

### Getting Help
1. Check documentation
2. Search existing issues
3. Ask in Discord
4. Create new issue

## Recognition

### Contributors
- Listed in CONTRIBUTORS.md
- Mentioned in release notes
- Project statistics tracked

### Rewards
- Recognition in documentation
- Community acknowledgment
- Feature attribution

## Project Structure

### Important Files
- README.md: Project overview
- CONTRIBUTING.md: This file
- CODE_OF_CONDUCT.md: Community guidelines
- LICENSE: Project license
- documentation/: Detailed docs

### Key Directories
- backend/: Java backend code
- webapp/: Frontend code
- documentation/: Project docs
- .github/: GitHub configs

## Release Process

### Version Numbers
Follow Semantic Versioning:
- MAJOR: Breaking changes
- MINOR: New features
- PATCH: Bug fixes

### Release Steps
1. Version bump
2. Changelog update
3. Documentation review
4. Release notes
5. Tag creation

## Questions?

If you have questions:
1. Read documentation
2. Check existing issues
3. Ask in Discord
4. Create new issue

Thank you for contributing to Three Player Chess! 