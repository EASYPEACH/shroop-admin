<template>
  <div>
    <main-title title="슈룹 수익률 월별 추이" />
    <Line :data="data" :options="options" />
  </div>
</template>

<script setup>
import { Line } from "vue-chartjs";
import { MainTitle } from "@/components/Title";

const data = {
  labels: ["January", "February", "March", "April", "May", "June", "July"],
  datasets: [
    {
      label: "Data One",
      backgroundColor: "#f87979",
      data: [40, 39, 10, 40, 39, 80, 40],
    },
  ],
};
const options = {
  responsive: true,
};
</script>
