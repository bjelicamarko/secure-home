<template>
  <div style="margin-top: 4%; margin-bottom: 2%">
    <h4 id="title-label">CSR data</h4>
    <table class="table table-bordered">
      <thead class="table-light">
        <th>Email</th>
        <th>Common name</th>
        <th>Organization</th>
        <th>Organization unit</th>
        <th>Country</th>
        <th>State</th>
        <th>City</th>
      </thead>
      <tbody>
        <tr>
          <td>{{ csr.email }}</td>
          <td>{{ csr.commonName }}</td>
          <td>{{ csr.organization }}</td>
          <td>{{ csr.organizationUnit }}</td>
          <td>{{ csr.country }}</td>
          <td>{{ csr.state }}</td>
          <td>{{ csr.city }}</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
export default {
  props: ["csr"],
};
</script>

<style></style>
