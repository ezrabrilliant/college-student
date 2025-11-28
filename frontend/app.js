/**
 * Connects to Spring Boot REST API
 */

const API_URL = 'http://localhost:8080/api/students';

// DOM Elements
const studentForm = document.getElementById('student-form');
const studentList = document.getElementById('student-list');
const formTitle = document.getElementById('form-title');
const formTitleIcon = document.querySelector('#form-title i');
const submitBtn = document.getElementById('submit-btn');
const btnText = document.getElementById('btn-text');
const btnIcon = document.getElementById('btn-icon');
const cancelBtn = document.getElementById('cancel-btn');
const editModeInput = document.getElementById('edit-mode');
const nomorIndukInput = document.getElementById('nomorInduk');
const namaDepanInput = document.getElementById('namaDepan');
const namaBelakangInput = document.getElementById('namaBelakang');
const tanggalLahirInput = document.getElementById('tanggalLahir');
const emptyState = document.getElementById('empty-state');
const loadingEl = document.getElementById('loading');
const tableEl = document.getElementById('student-table');

// SweetAlert2 Toast Configuration
const Toast = Swal.mixin({
    toast: true,
    position: 'top-end',
    showConfirmButton: false,
    timer: 3000,
    timerProgressBar: true,
    didOpen: (toast) => {
        toast.onmouseenter = Swal.stopTimer;
        toast.onmouseleave = Swal.resumeTimer;
    }
});

/**
 * Show success notification
 */
function showSuccess(message) {
    Toast.fire({
        icon: 'success',
        title: message
    });
}

/**
 * Show error notification
 */
function showError(message) {
    Toast.fire({
        icon: 'error',
        title: message
    });
}

/**
 * Fetch all students from API
 */
async function fetchStudents() {
    try {
        showLoading(true);
        const response = await fetch(API_URL);
        if (!response.ok) throw new Error('Failed to fetch students');
        const students = await response.json();
        renderStudents(students);
    } catch (error) {
        console.error('Error fetching students:', error);
        showError('Gagal memuat data mahasiswa');
    } finally {
        showLoading(false);
    }
}

/**
 * Create new student
 */
async function createStudent(studentData) {
    try {
        const response = await fetch(API_URL, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(studentData),
        });
        
        if (!response.ok) {
            const error = await response.json();
            throw new Error(error.message || 'Failed to create student');
        }
        
        const newStudent = await response.json();
        showSuccess(`Mahasiswa ${newStudent.namaLengkap} berhasil ditambahkan!`);
        return newStudent;
    } catch (error) {
        console.error('Error creating student:', error);
        showError(error.message);
        return null;
    }
}

/**
 * Update existing student
 */
async function updateStudent(nim, studentData) {
    try {
        const response = await fetch(`${API_URL}/${nim}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(studentData),
        });
        
        if (!response.ok) {
            const error = await response.json();
            throw new Error(error.message || 'Failed to update student');
        }
        
        const updatedStudent = await response.json();
        showSuccess(`Data ${updatedStudent.namaLengkap} berhasil diperbarui!`);
        return updatedStudent;
    } catch (error) {
        console.error('Error updating student:', error);
        showError(error.message);
        return null;
    }
}

/**
 * Delete student by NIM
 */
async function deleteStudent(nim) {
    try {
        const response = await fetch(`${API_URL}/${nim}`, {
            method: 'DELETE',
        });
        
        if (!response.ok) {
            const error = await response.json();
            throw new Error(error.message || 'Failed to delete student');
        }
        
        showSuccess('Mahasiswa berhasil dihapus!');
        return true;
    } catch (error) {
        console.error('Error deleting student:', error);
        showError(error.message);
        return false;
    }
}

/**
 * Fetch student detail by NIM (for edit form)
 */
async function fetchStudentDetail(nim) {
    try {
        const response = await fetch(`${API_URL}/${nim}`);
        if (!response.ok) throw new Error('Failed to fetch student detail');
        return await response.json();
    } catch (error) {
        console.error('Error fetching student detail:', error);
        showError('Gagal memuat detail mahasiswa');
        return null;
    }
}

/**
 * Render students to table
 */
function renderStudents(students) {
    if (students.length === 0) {
        tableEl.style.display = 'none';
        emptyState.style.display = 'block';
        return;
    }
    
    tableEl.style.display = 'table';
    emptyState.style.display = 'none';
    
    studentList.innerHTML = students.map(student => `
        <tr>
            <td><strong>${student.nomorInduk}</strong></td>
            <td>${student.namaLengkap}</td>
            <td>${student.usia} tahun</td>
            <td>
                <div class="action-buttons">
                    <button class="btn btn-edit" onclick="editStudent('${student.nomorInduk}')">
                        <i data-lucide="pencil"></i> Edit
                    </button>
                    <button class="btn btn-delete" onclick="confirmDelete('${student.nomorInduk}', '${student.namaLengkap}')">
                        <i data-lucide="trash-2"></i> Hapus
                    </button>
                </div>
            </td>
        </tr>
    `).join('');
    
    lucide.createIcons();
}

/**
 * Show/hide loading state
 */
function showLoading(show) {
    loadingEl.style.display = show ? 'block' : 'none';
    if (show) {
        tableEl.style.display = 'none';
        emptyState.style.display = 'none';
    }
}

/**
 * Switch to edit mode
 */
async function editStudent(nim) {
    // Fetch student detail from API (proper RESTful approach)
    const student = await fetchStudentDetail(nim);
    if (!student) {
        return;
    }
    
    editModeInput.value = nim;
    formTitle.innerHTML = '<i data-lucide="user-pen"></i> Edit Mahasiswa';
    btnText.textContent = 'Simpan Perubahan';
    btnIcon.setAttribute('data-lucide', 'save');
    lucide.createIcons();
    cancelBtn.style.display = 'inline-flex';
    nomorIndukInput.value = nim;
    nomorIndukInput.disabled = true;
    
    // Fill form with detail data
    namaDepanInput.value = student.namaDepan || '';
    namaBelakangInput.value = student.namaBelakang || '';
    tanggalLahirInput.value = student.tanggalLahir || '';
    
    // Focus on nama depan
    namaDepanInput.focus();
    
    // Scroll to form
    document.querySelector('.form-section').scrollIntoView({ behavior: 'smooth' });
}

/**
 * Reset form to create mode
 */
function resetForm() {
    studentForm.reset();
    editModeInput.value = 'false';
    formTitle.innerHTML = '<i data-lucide="user-plus"></i> Tambah Mahasiswa Baru';
    btnText.textContent = 'Tambah Mahasiswa';
    btnIcon.setAttribute('data-lucide', 'plus');
    lucide.createIcons();
    cancelBtn.style.display = 'none';
    nomorIndukInput.disabled = false;
}

/**
 * Confirm delete with SweetAlert2
 */
async function confirmDelete(nim, nama) {
    const result = await Swal.fire({
        title: 'Hapus Mahasiswa?',
        html: `Anda yakin ingin menghapus <strong>${nama}</strong> (${nim})?<br><small>Data yang dihapus tidak dapat dikembalikan.</small>`,
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#b44141',
        cancelButtonColor: '#6b7c93',
        confirmButtonText: 'Ya, Hapus!',
        cancelButtonText: 'Batal',
        reverseButtons: true
    });
    
    if (result.isConfirmed) {
        handleDelete(nim);
    }
}

/**
 * Handle delete action
 */
async function handleDelete(nim) {
    const success = await deleteStudent(nim);
    if (success) {
        fetchStudents();
    }
}

/**
 * Calculate age from birth date
 */
function calculateAge(birthDate) {
    const today = new Date();
    const birth = new Date(birthDate);
    let age = today.getFullYear() - birth.getFullYear();
    const monthDiff = today.getMonth() - birth.getMonth();
    if (monthDiff < 0 || (monthDiff === 0 && today.getDate() < birth.getDate())) {
        age--;
    }
    return age;
}

/**
 * Validate birth date (min 15 years old, max 100 years old)
 */
function validateBirthDate(birthDate) {
    const age = calculateAge(birthDate);
    
    if (age < 15) {
        showError('Usia minimal mahasiswa adalah 15 tahun');
        return false;
    }
    
    if (age > 100) {
        showError('Tanggal lahir tidak valid (usia melebihi 100 tahun)');
        return false;
    }
    
    return true;
}

/**
 * Handle form submission
 */
studentForm.addEventListener('submit', async (e) => {
    e.preventDefault();
    
    const nim = nomorIndukInput.value.trim();
    const namaDepan = namaDepanInput.value.trim();
    const namaBelakang = namaBelakangInput.value.trim() || null;
    const tanggalLahir = tanggalLahirInput.value;
    
    // Validate birth date
    if (!validateBirthDate(tanggalLahir)) {
        return;
    }
    
    const studentData = {
        namaDepan,
        namaBelakang,
        tanggalLahir,
    };
    
    const editMode = editModeInput.value;
    
    if (editMode !== 'false') {
        // Update mode
        const result = await updateStudent(editMode, studentData);
        if (result) {
            resetForm();
            fetchStudents();
        }
    } else {
        // Create mode
        studentData.nomorInduk = nim;
        const result = await createStudent(studentData);
        if (result) {
            resetForm();
            fetchStudents();
        }
    }
});

/**
 * Handle cancel button
 */
cancelBtn.addEventListener('click', () => {
    resetForm();
});

/**
 * Set date input constraints, set rules for min and max year (15 - 100 years old)
 */
function initDateConstraints() {
    const today = new Date();
    
    const maxDate = new Date(today.getFullYear() - 15, today.getMonth(), today.getDate());
    const minDate = new Date(today.getFullYear() - 100, today.getMonth(), today.getDate());
    
    tanggalLahirInput.max = maxDate.toISOString().split('T')[0];
    tanggalLahirInput.min = minDate.toISOString().split('T')[0];
}

// Load students on page load
document.addEventListener('DOMContentLoaded', () => {
    initDateConstraints();
    fetchStudents();
});
