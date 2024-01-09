<!-- eslint-disable vue/attribute-hyphenation -->
<template>
  <v-app id="app">
    <WriteForm :getData="getData" />

    <div class="board-container">
      <div class="board-header">
        <span class="header-item">번호</span>
        <span class="header-item">제목</span>
        <span class="header-item author">작성자</span>
        <span class="header-item createdAt">작성일</span>
        <span class="header-item">삭제</span>
      </div>
      <div class="board-list">
        <div v-for="item in bulletinList" :key="item.id" class="board-item">
          <span class="board-content-item">{{ item.id }}</span>
          <nuxt-link :to="`/detail/${item.id}`" class="board-content-item board-title">{{ item.title }}</nuxt-link>
          <span class="board-content-item">{{ item.userName }}</span>
          <span class="board-content-item">{{ item.createdAt }}</span>
          <span class="board-content-item delete-btn-container">
            <button @click="onDelete(item.id)" class="delete-btn">x</button>
          </span>
        </div>
      </div>
    </div>

    <PaginationVue
      :page="parmaId"
      :totalCount="totalCount"
      :itemsPerPage="COUNTITEM"
      :pagesPerGroup="5"
      @changePage="onPageChange"
    />
  </v-app>
</template>


<script>
import isToday from 'date-fns/isToday'
import { format } from 'date-fns'
import { COUNTITEM } from '../../constant'
import WriteForm from '../../components/WriteForm.vue'
import PaginationVue from '../../components/Pagination.vue'

export default {
  name: 'IndexPage',
  components: {
    WriteForm,
    PaginationVue,
  },

  data() {
    return {
      bulletinList: [],
      totalCount: 0,
      COUNTITEM,
    }
  },
  computed: {
    parmaId() {
      return +this.$route.params.page
    },
  },
  watch: {
    parmaId() {
      this.getData()
    },
  },
  mounted() {
    this.getData()
  },

  methods: {
    onPageChange(page) {
      this.$router.push(`/board/${page}`)
    },
    async getData() {
      try {
        const { body } = await this.$axios.$get('/api/get', {
          params: {
            page: this.parmaId,
            size: COUNTITEM,
          },
        })

        const data = body.content
        const total = body.totalElements

        // 특정 날짜가 오늘인지 확인 human readable format
        data.forEach(
          (item) =>
            (item.createdAt = isToday(new Date(item.createdAt))
              ? '오늘'
              : format(new Date(item.createdAt), 'yyyy-MM-dd')) // 날짜를 원하는 형식으로 포맷팅
        )
        this.bulletinList = data
        this.totalCount = total
      } catch {
        this.$router.push('/')
      }
    },

    async onDelete(itemID) {
      try {
        await this.$axios.$delete(`/api/delete/${itemID}`)
        await this.getData()
      } catch {
        alert('삭제권한 없습니다')
      }

      // if (message.includes('success')) {
      //   await this.getData()
      // } else {
      //   alert('삭제권한 없습니다')
      // }
    },
  },
}
</script>

<style scoped>
/*#app {*/
/*  display: flex;*/
/*  flex-direction: column;*/
/*  align-items: center;*/
/*  margin-top: 30px;*/
/*}*/

.board-container {
  width: 100%;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  border-radius: 8px;
  overflow: hidden;
}

.board-header, .board-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.header-item, .board-content-item {
  text-align: center;
  flex: 1;
  padding: 10px;
}



.board-header {
  background-color: #f5f5f5;
  font-weight: bold;
}

.board-list {
  background-color: white;
}

.board-title {
  color: #000;
  text-decoration: none;
}

.delete-btn-container {
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 0;
}

.delete-btn {
  background-color: red;
  color: white;
  border: none;
  border-radius: 4px;
  padding: 5px 10px;
  cursor: pointer;
  width: 30px;
  height: 30px;
}

.delete-btn:hover {
  background-color: darkred;
}

.pageNum, .clicked {
  /* Your pagination styles */
}
</style>
