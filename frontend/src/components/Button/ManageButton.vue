<template>
  <v-btn @click="handleClick" color="subBlue">{{ buttonText }}</v-btn>
</template>

<script setup>
defineProps({
  handleClick: Function,
  buttonText: String,
});
</script>
