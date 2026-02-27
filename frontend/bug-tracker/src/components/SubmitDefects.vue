<template>
  <div class="defects">
    <h2>Submit a New Defect</h2>
    <form @submit.prevent="handleSubmit">
      <div class="form-group">
        <label for="title">Title:</label>
        <input
          type="text"
          id="title"
          v-model="newDefect.title"
          required
          placeholder="Enter defect title"
        />
      </div>
      
      <div class="form-group">
        <label for="description">Description:</label>
        <textarea
          id="description"
          v-model="newDefect.description"
          required
          placeholder="Describe the defect"
          rows="4"
        ></textarea>
      </div>
      
      <div class="form-group">
        <label for="severity">Severity:</label>
        <select id="severity" v-model="newDefect.severity" required>
          <option value="Low">Low</option>
          <option value="Medium">Medium</option>
          <option value="High">High</option>
          <option value="Critical">Critical</option>
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
      
      <button type="submit" class="submit-btn">
        Submit Defect
      </button>
    </form>
    
    <div v-if="submittedDefect" class="submitted-message">
      <p>Defect submitted successfully!</p>
      <p><strong>Title:</strong> {{ submittedDefect.title }}</p>
      <p><strong>Description:</strong> {{ submittedDefect.description }}</p>
      <p><strong>Severity:</strong> {{ submittedDefect.severity }}</p>
      <p><strong>Status:</strong> {{ submittedDefect.status }}</p>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'

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
    const response = await fetch('http://localhost:8080/api/defects', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(newDefect.value)
    })
    
    if (response.ok) {
      const result = await response.json()
      submittedDefect.value = result
      // Clear form
      newDefect.value = {
        title: '',
        description: '',
        severity: 'Low',
        status: 'Open'
      }
    } else {
      throw new Error('Failed to submit defect')
    }
  } catch (error) {
    console.error('Error submitting defect:', error)
    alert('Error submitting defect. Please try again.')
  }
}

// Function to fetch defects (optional, for display)
const fetchDefects = async () => {
  try {
    const response = await fetch('http://localhost:8080/api/defects')
    const data = await response.json()
    // You can store this in a ref if you want to display the list
  } catch (error) {
    console.error('Error fetching defects:', error)
  }
}

// Fetch defects when the component is mounted
onMounted(() => {
  fetchDefects()
})

// Define props
defineProps({
  msg: {
    type: String,
    required: true,
  },
})
</script>