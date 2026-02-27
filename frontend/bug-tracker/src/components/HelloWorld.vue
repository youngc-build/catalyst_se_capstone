<template>
  <div class="greetings">
    <!-- <h1 class="green">{{ msg }}</h1> -->
    <!-- <h3>
      You’ve successfully created a project with what
      <a href="https://vite.dev/" target="_blank" rel="noopener">Vite</a> +
      <a href="https://vuejs.org/" target="_blank" rel="noopener">Vue 3</a>.
    </h3> -->
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

// Define a ref to hold the defects data
const defects = ref([])

// Function to fetch defects from the API
const fetchDefects = async () => {
  try {
    const response = await fetch('http://localhost:8080/api/defects')    
    const data = await response.json()
    defects.value = data
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

<!-- <style scoped>
h1 {
  font-weight: 500;
  font-size: 2.6rem;
  position: relative;
  top: -10px;
}

h3 {
  font-size: 1.2rem;
}

.greetings h1,
.greetings h3 {
  text-align: center;
}

@media (min-width: 1024px) {
  .greetings h1,
  .greetings h3 {
    text-align: left;
  }
}

.defects-list {
  margin-top: 20px;
}

.defects-list h2 {
  color: #333;
}

.defects-list ul {
  list-style-type: none;
  padding: 0;
}

.defects-list li {
  padding: 10px;
  border-bottom: 1px solid #ccc;
  margin-bottom: 10px;
}

.defects-list strong {
  color: #000;
}
</style> -->