<template>
  <div class="container">
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
                <textarea v-model="defect.description" rows="4"></textarea>
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
              <div class="button-group">
                <button @click="saveDefect(defect)" class="save-btn">Save</button>
                <button @click="cancelEdit(defect.id)" class="cancel-btn">Cancel</button>
              </div>
            </div>
            <div v-else class="view-mode">
              <strong>Title:</strong> {{ defect.title }}
              <br />
              <strong>Description:</strong> {{ defect.description }}
              <br />
              <strong>Severity:</strong> {{ defect.severity }}
              <br />
              <strong>Status:</strong> {{ defect.status }}
              <br />
              <button @click="startEdit(defect.id)" class="edit-btn">Edit</button>
              <button @click="confirmDelete(defect.id)" class="delete-btn">Delete</button>
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

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'

// Define a ref to hold the defects data
const defects = ref([])

// Define a ref to track which defect is currently being edited
const editingDefectId = ref(null)

// Function to fetch defects from the API using Axios
const fetchDefects = async () => {
  try {
    const response = await axios.get('http://localhost:8080/api/defects')
    defects.value = response.data
  } catch (error) {
    console.error('Error fetching defects:', error)
  }
}

// Fetch defects when the component is mounted
onMounted(() => {
  fetchDefects()
})

// Function to start editing a defect
const startEdit = (id) => {
  editingDefectId.value = id
}

// Function to save edited defect
const saveDefect = (defect) => {
  // Send updated defect to server
  axios.put(`http://localhost:8080/api/defects/${defect.id}`, defect)
    .then(() => {
      // Update the defect in our local state
      const index = defects.value.findIndex(d => d.id === defect.id)
      if (index !== -1) {
        defects.value[index] = defect
      }
      // Stop editing
      editingDefectId.value = null
    })
    .catch(error => {
      console.error('Error saving defect:', error)
      // Revert to previous state
      editingDefectId.value = null
    })
}

// Function to cancel editing
const cancelEdit = (id) => {
  // Stop editing
  editingDefectId.value = null
}

// Function to confirm delete
const confirmDelete = (id) => {
  // Show confirmation prompt
  if (confirm('Are you sure you want to delete this defect?')) {
    // Delete the defect from the API
    axios.delete(`http://localhost:8080/api/defects/${id}`)
      .then(() => {
        // Remove the defect from our local state
        const index = defects.value.findIndex(d => d.id === id)
        if (index !== -1) {
          defects.value.splice(index, 1)
        }
      })
      .catch(error => {
        console.error('Error deleting defect:', error)
      })
  }
}

// Define props
defineProps({
  msg: {
    type: String,
    required: true,
  },
})
</script>

<style scoped>
.container {
  padding: 20px;
  width: 100%;
  margin: 0 auto;
  max-width: 700px;
}

.list-defects {
  border: 1px solid #ccc;
  border-radius: 8px;
  padding: 20px;
  margin: 20px auto;
  width: 100%;
}

.defects-list {
  margin-top: 20px;
}

h2 {
  margin-bottom: 20px;
  font-weight: bold;
}

ul {
  list-style-type: none;
  padding: 0;
}

li {
  padding: 15px;
  border-bottom: 1px solid #eee;
  margin-bottom: 10px;
  background-color: #f8f9fa;
  border-radius: 4px;
}

li strong {
  display: block;
  margin-bottom: 5px;
  font-weight: bold;
}

li br {
  display: block;
  margin: 5px 0;
}

li:last-child {
  border-bottom: none;
}

/* Edit mode styles */
.edit-mode {
  display: flex;
  flex-direction: column;
  gap: 10px;
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

/* Button group */
.button-group {
  display: flex;
  gap: 10px;
  margin-top: 10px;
}

.save-btn,
.cancel-btn {
  padding: 8px 16px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.save-btn {
  background-color: #28a745;
  color: white;
}

.save-btn:hover {
  background-color: #218838;
}

.cancel-btn {
  background-color: #6c757d;
  color: white;
}

.cancel-btn:hover {
  background-color: #545b62;
}

/* View mode styles */
.view-mode {
  display: flex;
  flex-direction: column;
  gap: 5px;
}

.edit-btn {
  background-color: #007bff;
  color: white;
  padding: 5px 10px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.edit-btn:hover {
  background-color: #0056b3;
}

.delete-btn {
  background-color: #dc3545;
  color: white;
  padding: 5px 10px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.delete-btn:hover {
  background-color: #c82333;
}

/* Responsive design */
@media (max-width: 600px) {
  .form-group {
    width: 100%;
  }
}
</style>