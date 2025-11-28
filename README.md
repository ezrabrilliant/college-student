# College Student Management System

Aplikasi CRUD (Create, Read, Update, Delete) untuk mengelola data mahasiswa, dibangun dengan **Clean Architecture** menggunakan Java Spring Boot dan **Single Page Application (SPA)** frontend.

## 🎯 Features

- ✅ **CRUD Operations** - Create, Read, Update, Delete data mahasiswa
- ✅ **Clean Architecture** - Backend terstruktur dengan 4 layer
- ✅ **RESTful API** - Endpoint yang clean dan konsisten
- ✅ **SPA Frontend** - Modern UI tanpa page reload
- ✅ **Responsive Design** - Optimal di desktop dan mobile
- ✅ **Form Validation** - Validasi usia (15-100 tahun)
- ✅ **SweetAlert2** - Notifikasi dan dialog yang menarik

## 🏗️ Architecture

Project ini menggunakan **Clean Architecture** dengan 4 layer:

```
backend/src/main/java/com/college/student/
├── domain/                    # Domain Layer (innermost)
│   ├── entity/
│   │   └── Student.java       # Core entity dengan business logic
│   └── repository/
│       └── StudentRepository.java  # Interface (contract)
│
├── application/               # Application Layer
│   ├── dto/
│   │   ├── CreateStudentRequest.java
│   │   ├── UpdateStudentRequest.java
│   │   └── StudentResponse.java
│   └── service/
│       └── StudentService.java     # Business logic & validation
│
├── infrastructure/            # Infrastructure Layer
│   └── persistence/
│       └── JsonStudentRepository.java  # JSON file implementation
│
└── presentation/              # Presentation Layer (outermost)
    ├── controller/
    │   └── StudentController.java  # REST API endpoints
    └── exception/
        └── GlobalExceptionHandler.java  # Error handling
```

## 🛠️ Tech Stack

### Backend
- **Java 17**
- **Spring Boot 3.2.0**
- **Maven** (build tool)
- **Jackson** (JSON serialization)
- **JSON File** (data storage)

### Frontend
- **HTML5** - Semantic markup
- **CSS3** - Modern styling dengan CSS Grid & Flexbox
- **JavaScript (ES6+)** - Vanilla JS, no framework
- **Lucide Icons** - Icon library
- **SweetAlert2** - Beautiful alerts & notifications
- **Google Fonts (Inter)** - Typography

## 🚀 How to Run

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- Modern web browser

### 1. Run Backend

```bash
cd backend
mvn spring-boot:run
```

Server akan berjalan di `http://localhost:8080`

### 2. Run Frontend

Buka file `frontend/index.html` di browser, atau gunakan Live Server extension di VS Code.

> ⚠️ **Note:** Pastikan backend sudah berjalan sebelum menggunakan frontend.

## 📡 API Endpoints

Base URL: `http://localhost:8080/api/students`

### 1. Get All Students
```http
GET /api/students
```

**Response:**
```json
[
  {
    "nomorInduk": "C14220315",
    "namaLengkap": "Ezra Brilliant",
    "usia": 21
  }
]
```

### 2. Get Student by NIM
```http
GET /api/students/{nomorInduk}
```

**Response (200):**
```json
{
  "nomorInduk": "C14220315",
  "namaLengkap": "Ezra Brilliant",
  "usia": 21
}
```

**Response (404):**
```json
{
  "error": "Not Found",
  "message": "Student not found with NIM: XXX",
  "status": 404
}
```

### 3. Create Student
```http
POST /api/students
Content-Type: application/json

{
  "nomorInduk": "C14220001",
  "namaDepan": "Budi",
  "namaBelakang": "Santoso",
  "tanggalLahir": "2003-05-15"
}
```

**Response (200):**
```json
{
  "nomorInduk": "C14220001",
  "namaLengkap": "Budi Santoso",
  "usia": 22
}
```

**Validations:**
- `nomorInduk` - Required, must be unique
- `namaDepan` - Required
- `namaBelakang` - Optional
- `tanggalLahir` - Required, format: `yyyy-MM-dd`

### 4. Update Student
```http
PUT /api/students/{nomorInduk}
Content-Type: application/json

{
  "namaDepan": "Budi Updated",
  "namaBelakang": "Wijaya",
  "tanggalLahir": "2003-06-20"
}
```

### 5. Delete Student
```http
DELETE /api/students/{nomorInduk}
```

**Response:** `204 No Content`

## 📁 Data Storage

Data disimpan di file JSON:
```
backend/src/main/resources/data/students.json
```

Format:
```json
[
  {
    "nomorInduk": "C14220315",
    "namaDepan": "Ezra",
    "namaBelakang": "Brilliant",
    "tanggalLahir": "2004-08-27"
  }
]
```

> Note: `namaLengkap` dan `usia` adalah **computed properties** yang dihitung dari data tersimpan, bukan disimpan langsung.

## 🧪 Test dengan PowerShell

```powershell
# Get all students
Invoke-RestMethod -Uri "http://localhost:8080/api/students" -Method GET

# Create student
Invoke-RestMethod -Uri "http://localhost:8080/api/students" -Method POST `
  -ContentType "application/json" `
  -Body '{"nomorInduk":"C14220001","namaDepan":"Budi","namaBelakang":"Santoso","tanggalLahir":"2003-05-15"}'

# Update student
Invoke-RestMethod -Uri "http://localhost:8080/api/students/C14220001" -Method PUT `
  -ContentType "application/json" `
  -Body '{"namaDepan":"Budiyono","namaBelakang":"Wijaya","tanggalLahir":"2003-06-20"}'

# Delete student
Invoke-RestMethod -Uri "http://localhost:8080/api/students/C14220001" -Method DELETE
```

## 📝 Design Decisions

### Rich Domain Model
Entity `Student` memiliki method `getNamaLengkap()` dan `getUsia()` yang menghitung nilai dari data yang tersimpan. Menggunakan `@JsonIgnore` untuk mencegah Jackson menyimpan computed properties ke file JSON.

### Clean Architecture Benefits
- **Separation of Concerns** - Setiap layer punya tanggung jawab sendiri
- **Testability** - Mudah di-unit test karena dependency injection
- **Flexibility** - Mudah ganti implementasi (misal: dari JSON ke Database)
- **Independence** - Domain layer tidak depend ke framework

### Separate Response DTOs
- `StudentResponse` - Untuk list (namaLengkap, usia)
- `StudentDetailResponse` - Untuk edit form (namaDepan, namaBelakang, tanggalLahir)

## 🖥️ Frontend Structure

```
frontend/
├── index.html      # Main HTML file
├── style.css       # Styling dengan CSS Variables
└── app.js          # Application logic & API calls
```

### UI Features
- **Modern Design** - Soft gradient background, rounded corners
- **Toast Notifications** - SweetAlert2 toast di pojok kanan atas
- **Delete Confirmation** - Dialog konfirmasi sebelum hapus
- **Form Validation** - Validasi tanggal lahir (usia 15-100 tahun)
- **Loading State** - Spinner saat fetch data
- **Empty State** - Pesan ketika tidak ada data
- **Responsive** - Mobile-friendly layout


## 👤 Author

Ezra Brilliant - C14220315