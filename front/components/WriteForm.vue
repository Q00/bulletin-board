<template>
  <v-app id="app">
    <div class="dialog-container">
      <v-dialog v-model="dialog" max-width="800px" class="dialog">
        <template #activator="{ on, attrs }">
          <v-btn v-bind="attrs" v-on="on" class="write-btn">
            글쓰기
          </v-btn>
        </template>

        <v-card class="dialog-card">
          <v-card-title class="title-header">
            새 글 쓰기
          </v-card-title>

          <v-form v-model="valid">
            <v-container>
              <v-row>
                <v-col cols="12">
                  <v-text-field
                    v-model="title"
                    :counter="60"
                    label="제목"
                    filled
                    required
                  ></v-text-field>
                </v-col>

                <v-col cols="12">
                  <v-textarea
                    v-model="content"
                    label="내용"
                    filled
                    rows="5"
                    no-resize
                  ></v-textarea>
                </v-col>
              </v-row>
            </v-container>
          </v-form>

          <v-divider></v-divider>

          <v-card-actions class="dialog-actions">
            <v-spacer></v-spacer>
            <v-btn color="green darken-1" text @click="onClickCreate">등록</v-btn>
            <v-btn text @click="dialog = false">취소</v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </div>
  </v-app>
</template>

<script>
export default {
  // eslint-disable-next-line no-undef
  props: ['getData'],
  data() {
    return {
      dialog: false,
      valid: false,
      title: '',
      content: '',
    }
  },
  watch: {
    dialog() {
      if (this.dialog) {
        // eslint-disable-next-line no-useless-return
        return
      } else {
        // eslint-disable-next-line no-sequences
        ;(this.title = ''), (this.content = '')
      }
    },
  },
  methods: {
    // post요청시 바디에 담은 객체는 브라우저에서 "문자로" 변환해서 보내짐.
    // 때문에 body-parser을 통해 문자열을 다시 객체로 바꿔주는 것임.
    async onClickCreate() {
      this.dialog = false
      // post 요청
      const data = await this.$axios.$post('/api/post', {
        title: this.title,
        content: this.content,
      })
      window.location.href = `/board/1`
      // this.$emit('updated-get', true)
    },
  },
}
</script>
<style>
.v-application--wrap {
  min-height: 5vh !important;
}



.dialog-container {
  text-align: center;
  padding: 20px;
}

.write-btn {
  padding: 10px 30px;
  font-size: 1.1rem;
  font-weight: 500;
  box-shadow: 2px 2px 5px rgba(0, 0, 0, 0.2);
}

.dialog {
  display: flex;
  justify-content: center;
}

.dialog-card {
  border-radius: 10px;
}



.dialog-actions {
  justify-content: flex-end;
  padding: 20px;
}


</style>


</style>
