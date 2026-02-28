// bug-tracker/src/components/SubmitDefects.vue
<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'

// Define form data
const newDefect = ref({
  title: '',
  description: '',
  severity: 'Low',
  status: 'Open'
})

// Define submitted defect
const submittedDefect = ref(null)

// Function to handle form submission
const handleSubmit = async () => {
  try {
    const response = await axios.post(
      'http://localhost:8080/api/defects',
      newDefect.value
    )

    submittedDefect.value = response.data

    // Clear form
    newDefect.value = {
      title: '',
      description: '',
      severity: 'Low',
      status: 'Open'
    }
  } catch (error) {
    console.error('Error submitting defect:', error)
    alert('Error submitting defect. Please try again.')
  }
}

// Function to fetch defects (optional, for display)
const fetchDefects = async () => {
  try {
    const response = await axios.get('http://localhost:8080/api/defects')
    const data = response.data
    // You can store this in a ref if you want to display the list
  } catch (error) {
    console.error('Error fetching defects:', error)
  }
}

onMounted(() => {
  fetchDefects()
})

defineProps({
  msg: {
    type: String,
    required: true,
  },
})
</script>

<template>
  <div class="submit-defect-form">
    <h2>Submit a New Defect</h2>
    <form @submit.prevent="handleSubmit">
      <div class="form-group">
        <label for="title">Title:</label>
        <input
          type="text"
          id="title"
          v-model="newDefect.title"
          required
        />
      </div>
      <div class="form-group">
        <label for="description">Description:</label>
        <textarea
          id="description"
          v-model="newDefect.description"
          rows="4"
          required
        ></textarea>
      </div>
      <div class="form-group">
        <label for="severity">Severity:</label>
        <select
          id="severity"
          v-model="newDefect.severity"
          required
        >
          <option value="Low">Low</option>
          <option value="Medium">Medium</option>
          <option value="High">High</option>
        </select>
      </div>
      <div class="form-group">
        <label for="status">Status:</label>
        <select
          id="status"
          v-model="newDefect.status"
          required
        >
          <option value="Open">Open</option>
          <option value="In Progress">In Progress</option>
          <option value="Resolved">Resolved</option>
        </select>
      </div>
      <button type="submit">Submit Defect</button>
    </form>

    <div v-if="submittedDefect" class="submitted-defect">
      <h3>Defect Submitted Successfully!</h3>
      <p><strong>Title:</strong> {{ submittedDefect.title }}</p>
      <p><strong>Description:</strong> {{ submittedDefect.description }}</p>
      <p><strong>Severity:</strong> {{ submittedDefect.severity }}</p>
      <p><strong>Status:</strong> {{ submittedDefect.status }}</p>
    </div>
  </div>
</template>

<style scoped>
.submit-defect-form {
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
  padding: 10px 20px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
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
</style>