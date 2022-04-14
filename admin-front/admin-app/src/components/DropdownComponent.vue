<template>
  <div style="margin-top: 80px">
    <select v-model="selectedValue" @change="passEvent()">
      <option v-for="item in items" :key="item">{{ item }}</option>
    </select>

    Selected Item: {{ selectedValue }}
  </div>
</template>

<script>
export default {
  props: ["items"],
  data: function () {
    return {
      selectedValue: "root",
    };
  },
  methods: {
    passEvent: function () {
      this.$emit("changeItem", this.selectedValue);
    },
  },
};
</script>
