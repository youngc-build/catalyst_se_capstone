<template>
  <div class="defects">
    <div v-if="defects.length > 0" class="defects-list">
      <h2>Defects</h2>
      <ul>
        <li v-for="defect in defects" :key="defect.id">
          <strong>Title:</strong> {{ defect.title }}
          <br />
          <strong>Description:</strong> {{ defect.description }}
          <br />
          <strong>Severity:</strong> {{ defect.severity }}
          <br />
          <strong>Status:</strong> {{ defect.status }}
        </li>
      </ul>
    </div>
    <div v-else>
      <p>No defects found.</p>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'

// Define a ref to hold the defects data
const defects = ref([])

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

// Define props
defineProps({
  msg: {
    type: String,
    required: true,
  },
})
</script>