<script setup>
import SubmitDefects from './components/SubmitDefects.vue';
</script>

<template>
  <header>
    <div class="container">
      <div class="wrapper">
        <SubmitDefects />
      </div>
    </div>
  </header>
</template>

<style scoped>
.container {
  display: flex;
  gap: 20px; /* Optional: space between components */
  justify-content: space-between; /* Optional: distribute evenly */
  padding: 20px;
}

.wrapper {
  flex: 1; /* Each component takes equal space */
  min-width: 0; /* Prevents flex items from collapsing */
}

/* Optional: Add some padding or margin to components */
.wrapper > * {
  margin: 0;
}
</style>