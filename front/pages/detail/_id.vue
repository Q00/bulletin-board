<template>
  <div class="mainWrapper">
    <button type="button" class="backBtn" @click="gotoBack" aria-label="Go Back">←</button>
    <div class="itemDetails">
      <h1>{{ titleData }}</h1>
      <p><strong>User:</strong> {{ itemData.userName }}</p>
      <p><strong>Created At:</strong> {{ itemData.createdAt }}</p>
      <p><strong>Contents:</strong></p>
      <textarea v-model="itemData.contents" rows="10" class="contentsEdit"></textarea>
      <p><strong>Hits:</strong> {{ itemData.hits }}</p>

      <button class="modifyBtn" @click="onModify">Save Changes</button>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Detail',
  data() {
    return {
      itemData: {
        userName: '',
        createdAt: '',
        contents: '',
        hits: 0,
      },
      titleData: '',
      storedTitle: '',
    }
  },
  async created() {
    this.fetchItemData();
  },
  methods: {
    async fetchItemData() {
      try {
        const id = this.$route.params.id;
        const res = await this.$axios.$get(`/api/get/${id}`, {
          headers: {
            Authorization: `Bearer ${window.localStorage.getItem('ACCESS_TOKEN')}`,
          },
        });
        this.itemData = res.body;
        this.titleData = res.body.title;
        this.storedTitle = res.title;
      } catch (error) {
        console.error('Error fetching item data:', error);
        // Handle error appropriately
      }
    },
    async onModify() {
      const id = this.$route.params.id;
      try {
        await this.$axios.$patch(`/api/patch/${id}`, {
          title: this.titleData,
          contents: this.itemData.contents, // Send only the contents to be updated
        }, {
          headers: {
            Authorization: `Bearer ${window.localStorage.getItem('ACCESS_TOKEN')}`,
          },
        });
        // Fetch updated data
        await this.fetchItemData();
        alert('Contents successfully updated');
      } catch (error) {
        console.error('Error modifying contents:', error);
        // Handle error appropriately
      }
    },
    gotoBack() {
      this.$router.go(-1);
    },
  },
}
</script>

<style >
.mainWrapper {
  display: flex;
  flex-direction: column;
  align-items: center; 
  justify-content: center; 
  height: 100vh; 
  width: 100vw;
  padding: 20px;
  box-sizing: border-box; 
}

.itemDetails {
  width: 100%; 
  max-width: 600px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  border-radius: 8px;
  padding: 20px;
  background-color: #fff;
}

.itemDetails h1 {
  font-size: 24px;
  color: #333;
  margin-bottom: 20px;
}

.itemDetails p {
  font-size: 18px;
  margin: 10px 0;
  color: #666;
}

.itemDetails p strong {
  font-weight: bold;
  color: #333;
}

.button, .backBtn, .modifyBtn {
  padding: 15px 30px;
  margin-top: 25px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-size: 18px;
  transition: background-color 0.3s ease;
}

.modifyBtn {
  background-color: #0084ff;
  color: white;
}

.modifyBtn:hover {
  background-color: #005ecb;
}

.backBtn {
  background-color: #ff4757;
  color: white;
}

.backBtn:hover {
  background-color: #c0392b;
}
</style>

</style>
