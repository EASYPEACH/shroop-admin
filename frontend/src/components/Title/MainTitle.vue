<template>
  <h1>{{ title }}</h1>
</template>

<script setup>
defineProps({
  title: String,
});
</script>

<style lang="scss" scoped>
h1 {
  text-align: center;
  margin: 50px 0;
  font-size: 25px;
}
</style>
