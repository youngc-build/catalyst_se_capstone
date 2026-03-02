<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'

// ---- shared defects state ----
const defects = ref([])

// ---- list editing state ----
const editingDefectId = ref(null)

// ---- form state ----
const newDefect = ref({
  title: '',
  description: '',
  severity: 'Low',
  status: 'Open',
})

// ---- fetch all defects ----
const fetchDefects = async () => {
  try {
    const response = await axios.get('http://localhost:8080/api/defects')
    defects.value = response.data
  } catch (error) {
    console.error('Error fetching defects:', error)
  }
}

onMounted(() => {
  fetchDefects()
})

// ---- submit new defect ----
const handleSubmit = async () => {
  try {
    await axios.post('http://localhost:8080/api/defects', newDefect.value)

    // clear form
    newDefect.value = {
      title: '',
      description: '',
      severity: 'Low',
      status: 'Open',
    }

    // refresh list
    await fetchDefects()
  } catch (error) {
    console.error('Error submitting defect:', error)
    alert('Error submitting defect. Please try again.')
  }
}

// ---- list edit handlers ----
const startEdit = (id) => {
  editingDefectId.value = id
}

const saveDefect = (defect) => {
  axios
    .put(`http://localhost:8080/api/defects/${defect.id}`, defect)
    .then(() => {
      const index = defects.value.findIndex((d) => d.id === defect.id)
      if (index !== -1) {
        defects.value[index] = { ...defect }
      }
      editingDefectId.value = null
    })
    .catch((error) => {
      console.error('Error saving defect:', error)
      editingDefectId.value = null
    })
}

const cancelEdit = () => {
  editingDefectId.value = null
  fetchDefects()
}

const confirmDelete = (id) => {
  if (confirm('Are you sure you want to delete this defect?')) {
    axios
      .delete(`http://localhost:8080/api/defects/${id}`)
      .then(() => {
        const index = defects.value.findIndex((d) => d.id === id)
        if (index !== -1) {
          defects.value.splice(index, 1)
        }
      })
      .catch((error) => {
        console.error('Error deleting defect:', error)
      })
  }
}


const formattedDescription = (desc) => {
    if (!desc) return '';
    return desc
      .replace(/\n+$/, '')   // remove trailing newlines
      .replace(/\n/g, '<br>');
  }

</script>

<template>
  <div class="container">
    <!-- submit form -->
    <div class="submit-defect-form">
      <h2>Submit a New Defect</h2>
      <form @submit.prevent="handleSubmit">
        <div class="form-group">
          <label for="title">Title:</label>
          <input id="title" v-model="newDefect.title" type="text" required />
        </div>
        <div class="form-group">
          <label for="description">Description:</label>
          <textarea id="description" v-model="newDefect.description" rows="6" style="white-space: pre-line;"
            placeholder="Enter description" required></textarea>
        </div>
        <div class="form-group">
          <label for="severity">Severity:</label>
          <select id="severity" v-model="newDefect.severity" required>
            <option value="Low">Low</option>
            <option value="Medium">Medium</option>
            <option value="High">High</option>
          </select>
        </div>
        <div class="form-group">
          <label for="status">Status:</label>
          <select id="status" v-model="newDefect.status" required>
            <option value="Open">Open</option>
            <option value="In Progress">In Progress</option>
            <option value="Resolved">Resolved</option>
          </select>
        </div>
        <button type="submit">Submit Defect</button>
      </form>
    </div>

    <!-- list defects (your code) -->
    <div class="list-defects">
      <div v-if="defects.length > 0" class="defects-list">
        <h2>Defects</h2>
        <ul>
          <li v-for="defect in defects" :key="defect.id">
            <div v-if="editingDefectId === defect.id" class="edit-mode">
              <div class="form-group">
                <label>Title:</label>
                <input type="text" v-model="defect.title" />
              </div>
              <div class="form-group">
                <label>Description:</label>
                <textarea v-model="defect.description" rows="6" style="white-space: pre-line;"
                  placeholder="Enter description"></textarea>
              </div>
              <div class="form-group">
                <label>Severity:</label>
                <select v-model="defect.severity">
                  <option value="Low">Low</option>
                  <option value="Medium">Medium</option>
                  <option value="High">High</option>
                </select>
              </div>
              <div class="form-group">
                <label>Status:</label>
                <select v-model="defect.status">
                  <option value="Open">Open</option>
                  <option value="In Progress">In Progress</option>
                  <option value="Resolved">Resolved</option>
                </select>
              </div>
              <div class="button-group right-aligned">
                <button @click="saveDefect(defect)" class="save-btn">
                  Save
                </button>
                <button @click="cancelEdit(defect.id)" class="cancel-btn">
                  Cancel
                </button>
              </div>
            </div>
            <div v-else class="view-mode">
              <strong>Title:</strong> {{ defect.title }}<br />
              <strong>Description:</strong>
<div v-html="formattedDescription(defect.description)"></div>
              <strong>Severity:</strong> {{ defect.severity }}<br />
              <strong>Status:</strong> {{ defect.status }}<br />
              <div class="button-group right-aligned">
                <button @click="startEdit(defect.id)" class="edit-btn">
                  Edit
                </button>
                <button @click="confirmDelete(defect.id)" class="delete-btn">
                  Delete
                </button>
              </div>
            </div>
          </li>
        </ul>
      </div>
      <div v-else>
        <p>No defects found.</p>
      </div>
    </div>
  </div>
</template>

<style scoped>
.submit-defect-form {
  width: 100%;
  max-width: 600px;
  margin: 20px auto;
  padding: 20px;
  border: 1px solid #ccc;
  border-radius: 8px;
}

.list-defects {
  width: 100%;
  max-width: 600px;
  margin: 20px auto;
  padding: 20px;
  border: 1px solid #ccc;
  border-radius: 8px;
}

.form-group {
  margin-bottom: 15px;
}

.form-group label {
  display: block;
  margin-bottom: 5px;
  font-weight: bold;
}

.form-group input,
.form-group textarea,
.form-group select {
  width: 100%;
  padding: 8px;
  border: 1px solid #ddd;
  border-radius: 4px;
}

.form-group textarea {
  resize: vertical;
}

button {
  background-color: #007bff;
  color: white;
  padding: 8px 16px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 14px;
}

button:hover {
  background-color: #0056b3;
}

.submitted-defect {
  margin-top: 20px;
  padding: 15px;
  border: 1px solid #007bff;
  border-radius: 4px;
  background-color: #f8f9fa;
}

/* Add spacing between buttons and consistent sizing */
.edit-btn,
.delete-btn,
.save-btn,
.cancel-btn {
  padding: 8px 16px;
  font-size: 14px;
  border-radius: 4px;
  margin-right: 8px;
}

.edit-btn {
  background-color: #28a745;
}

.edit-btn:hover {
  background-color: #218838;
}

.delete-btn {
  background-color: #dc3545;
}

.delete-btn:hover {
  background-color: #c82333;
}

.save-btn {
  background-color: #28a745;
}

.save-btn:hover {
  background-color: #218838;
}

.cancel-btn {
  background-color: #6c757d;
}

.cancel-btn:hover {
  background-color: #545b62;
}

.button-group.right-aligned {
  display: flex;
  justify-content: flex-end;
  gap: 8px;
  margin-top: 10px;
}

/* Optional: Add padding to the right for better spacing */
.edit-mode {
  padding-right: 16px;
}
</style>
