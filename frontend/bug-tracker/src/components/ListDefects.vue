<template>
  <div class="container">
    <div class="list-defects">
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

<style scoped>
.container {
  padding: 20px;
  width: 100%; /* Maximizes container width */
  margin: 0 auto; /* Centers it if needed */
  max-width: 700px;
}

.list-defects {
  border: 1px solid #ccc;
  border-radius: 8px;
  padding: 20px;
  margin: 20px auto;
  width: 100%; /* Ensures it takes full width */
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
</style>