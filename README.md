# WebNote

A web-based note management application built with Java, Servlets, and JSP. WebNote allows users to create, organize, search, and manage notes with rich content including text and images.

<img width="1277" alt="Screenshot 2025-03-13 at 12 47 52" src="https://github.com/user-attachments/assets/9406f223-a0ed-4b74-add8-da7a19c42acc" />

## 📋 Table of Contents

- [Features](#features)
- [Technologies](#technologies)
- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Running the Application](#running-the-application)
- [Usage](#usage)
- [API Endpoints](#api-endpoints)
- [Data Storage](#data-storage)
- [Development](#development)
- [License](#license)

## ✨ Features

- **Note Management**: Create, edit, view, and delete notes with rich content
- **Category Organization**: Organize notes into custom categories
- **Rich Content Support**: 
  - Text content
  - Image uploads
  - URL attachments
- **Search Functionality**: Search notes by keyword and filter by category
- **Persistent Storage**: JSON-based file storage system
- **Responsive Web Interface**: Clean and intuitive user interface built with JSP
- **Embedded Tomcat Server**: Built-in server for easy deployment

## 🛠 Technologies

- **Java 23**: Core programming language
- **Apache Tomcat 11.0.4**: Embedded web server
- **JSP 4.0.1**: Server-side templating
- **Maven**: Build automation and dependency management
- **Jackson**: JSON processing for data persistence
- **Servlets**: Backend request handling
- **HTML/CSS**: Frontend presentation

## 📁 Project Structure

```
WebNote/
├── src/
│   └── main/
│       ├── java/uk/ac/ucl/
│       │   ├── main/
│       │   │   └── Main.java                 # Application entry point
│       │   ├── model/
│       │   │   ├── Note.java                 # Note entity
│       │   │   ├── NoteContent.java          # Content model
│       │   │   ├── NoteService.java          # Business logic service
│       │   │   ├── NoteRepository.java       # Repository interface
│       │   │   ├── JsonNoteRepository.java   # JSON persistence implementation
│       │   │   ├── NoteFactory.java          # Note creation factory
│       │   │   └── NoteSearch.java           # Search functionality
│       │   └── servlets/
│       │       ├── CreateNoteServlet.java    # Create new notes
│       │       ├── SaveNoteServlet.java      # Save note updates
│       │       ├── NoteServlet.java          # View note details
│       │       ├── SearchServlet.java        # Search notes
│       │       ├── UploadImageServlet.java   # Handle image uploads
│       │       ├── ManageCategory.java       # Category management
│       │       └── ...                       # Other servlets
│       └── webapp/
│           ├── index.jsp                     # Home page
│           ├── note.jsp                      # Note detail view
│           ├── noteList.jsp                  # List all notes
│           ├── search.jsp                    # Search page
│           ├── searchResult.jsp              # Search results
│           ├── styles.css                    # Stylesheets
│           └── WEB-INF/
│               └── web.xml                   # Web application config
├── data/
│   ├── index.json                            # Note index
│   ├── categoryCollection.json               # Categories
│   ├── notes/                                # Individual note files
│   └── images/                               # Uploaded images
├── pom.xml                                   # Maven configuration
└── README.md                                 # This file
```

## 📋 Prerequisites

- **Java Development Kit (JDK) 23** or higher
- **Apache Maven 3.6+**
- **Git** (for cloning the repository)

## 🚀 Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/spicyGrape/WebNote.git
   cd WebNote
   ```

2. **Build the project**
   ```bash
   mvn clean install
   ```

   This will:
   - Compile the Java source files
   - Process resources
   - Run tests (if any)
   - Package the application as a WAR file in `war-file/` directory

## ▶️ Running the Application

### Option 1: Using Maven Exec Plugin

```bash
mvn exec:exec
```

### Option 2: Run the Main class directly

```bash
mvn compile
java -cp target/classes uk.ac.ucl.main.Main
```

The application will start an embedded Tomcat server and be accessible at:

```
http://localhost:8080
```

To stop the server, press `Ctrl+C` in the terminal.

## 💡 Usage

### Creating a Note
1. Navigate to the home page
2. Click "Create New" or go to `/createNote.html`
3. Add a title and content (text, images, URLs)
4. Save the note

### Organizing with Categories
1. Create a new category from the category management page
2. Assign notes to categories
3. Filter notes by category

### Searching Notes
1. Go to the Search page
2. Enter keywords
3. Optionally filter by category
4. View matching results

### Uploading Images
1. Open or create a note
2. Use the image upload feature
3. Images are stored in `data/images/`

## 🔌 API Endpoints

### Note Management
- `GET /noteList.html` - View all notes
- `GET /createNote.html` - Create note form
- `POST /createNote` - Create new note
- `GET /note.html?id={noteId}` - View specific note
- `POST /saveNote` - Save note updates
- `POST /deleteNote` - Delete note

### Content Management
- `POST /uploadImage` - Upload image
- `POST /uploadUrl` - Add URL content
- `POST /deleteContent` - Delete note content

### Category Management
- `POST /manageCategory` - Create/delete categories
- `POST /addRemoveNoteToCategory` - Add/remove note from category

### Search
- `GET /search.html` - Search page
- `POST /search` - Perform search

### Images
- `GET /image?fileName={fileName}` - Serve uploaded images

## 💾 Data Storage

WebNote uses a JSON-based file system for data persistence:

- **index.json**: Master index of all notes
- **categoryCollection.json**: Category definitions and note assignments
- **notes/*.json**: Individual note files with full content
- **images/**: Uploaded image files

All data is stored in the `data/` directory at the project root.

## 🔧 Development

### Building for Production

```bash
mvn clean package
```

This creates a WAR file in `war-file/` that can be deployed to any servlet container.

### Project Configuration

Key configuration in `pom.xml`:
- Java version: 23
- Tomcat version: 11.0.4
- JSP version: 4.0.1
- Main class: `uk.ac.ucl.main.Main`

### Logging

Application logs are written to `logfile.txt` in the project root.

## 📝 Notes

- This project was originally developed as coursework for COMP0004 Object-Oriented Programming at UCL
- The application uses an embedded Tomcat server for easy development and deployment
- Data is persisted as JSON files in the `data/` directory

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License

This project is part of academic coursework. Please check with the repository owner for usage rights.

## 👤 Author

**spicyGrape**
- GitHub: [@spicyGrape](https://github.com/spicyGrape)

---

**Happy Note-Taking! 📝**
