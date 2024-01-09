<template>
  <div class="pagination-container">
    <ul class="pagination-list">
      <li>
        <button class="pagination-btn" :disabled="currentGroup === 1" @click="previousGroup">
          «
        </button>
      </li>
      <li>
        <button class="pagination-btn" :disabled="pageNum === 1" @click="previousPage">
          ‹
        </button>
      </li>
      <li
        v-for="num in list"
        :key="num"
        :class="{ 'pagination-btn': true, 'pagination-btn--active': pageNum === num }"
      >
        <button class="pagination-btn" @click="gotoPage(num)">
          {{ num }}
        </button>
      </li>
      <li>
        <button class="pagination-btn" :disabled="pageNum === totalPages" @click="nextPage">
          ›
        </button>
      </li>
      <li>
        <button class="pagination-btn" :disabled="currentGroup === totalGroups" @click="nextGroup">
          »
        </button>
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  props: {
    page: { type: Number, default: 1 },
    totalCount: { type: Number, required: true },
    itemsPerPage: { type: Number, default: 3 },
    pagesPerGroup: { type: Number, default: 5 },
  },
  emits: ['changePage'],
  data() {
    return {
      pageNum: this.page,
    };
  },
  computed: {
    totalPages() {
      return Math.ceil(this.totalCount / this.itemsPerPage);
    },
    totalGroups() {
      return Math.ceil(this.totalPages / this.pagesPerGroup);
    },
    currentGroup() {
      return Math.max(Math.ceil(this.pageNum / this.pagesPerGroup), 1);
    },
    list() {
      const pages = [];
      const groupStart = (this.currentGroup - 1) * this.pagesPerGroup + 1;
      const groupEnd = Math.min(groupStart + this.pagesPerGroup - 1, this.totalPages);

      for (let num = groupStart; num <= groupEnd; num++) {
        pages.push(num);
      }

      return pages;
    },
  },
  watch: {
    page(newPage) {
      this.pageNum = Math.max(newPage, 1);
    },
  },
  methods: {
    previousPage() {
      if (this.pageNum > 1) {
        this.pageNum -= 1;
        this.$emit('changePage', this.pageNum);
      }
    },
    nextPage() {
      if (this.pageNum < this.totalPages) {
        this.pageNum += 1;
        this.$emit('changePage', this.pageNum);
      }
    },
    previousGroup() {
      if (this.currentGroup > 1) {
        this.pageNum = (this.currentGroup - 1) * this.pagesPerGroup - (this.pagesPerGroup - 1);
        this.$emit('changePage', this.pageNum);
      }
    },
    nextGroup() {
      if (this.currentGroup < this.totalGroups) {
        this.pageNum = this.currentGroup * this.pagesPerGroup + 1;
        this.$emit('changePage', this.pageNum);
      }
    },
    gotoPage(num) {
      this.pageNum = num;
      this.$emit('changePage', num);
    },
  },
};
</script>
<style>
.pagination-container {
  display: flex;
  justify-content: center;
  margin-top: 20px;
}

.pagination-list {
  list-style: none;
  padding: 0;
  display: flex;
  align-items: center;
}

.pagination-btn {
  background-color: transparent;
  border: 2px solid #dee2e6;
  color: #6c757d;
  margin: 0 5px;
  border-radius: 5px;
  cursor: pointer;
  transition: color 0.3s, background-color 0.3s, border-color 0.3s;
  padding: 5px 10px;
}

.pagination-btn:disabled {
  cursor: not-allowed;
  opacity: 0.5;
}

.pagination-btn:not(:disabled):hover {
  color: #fff;
  background-color: #007bff;
  border-color: #007bff;
}

.pagination-btn--active {
  font-weight: bold;
  color: #fff;
  background-color: #007bff;
  border-color: #007bff;
}
</style>
