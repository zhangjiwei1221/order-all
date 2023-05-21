<template>
  <div id="app">
    <van-search v-model="keyword" placeholder="搜索"/>
    <van-tabs v-model="active" :sticky="true">
      <van-tab v-for="tab in ['蔬菜', '豆制品']" :title="tab" :key="tab">
        <van-list v-if="indexList.length">
          <van-index-bar :index-list="indexList">
            <div v-for="anchor in indexList" :key="anchor">
              <van-index-anchor :index="anchor" style="text-align: left">{{
                  anchor
                }}
              </van-index-anchor>
              <van-cell
                v-for="(item, index) in productShowGroup[anchor]"
                :key="index"
              >
                <van-row>
                  <van-col span="6">
                    <van-field :value="item.name" readonly/>
                  </van-col>
                  <van-col span="5">
                    <van-field
                      v-model="item.num"
                      class="num-filed"
                      type="digit"
                    />
                  </van-col>
                  <van-col span="5">
                    <van-dropdown-menu>
                      <van-dropdown-item
                        v-model="item.unit"
                        :options="unitMap[item.unitKey]"
                      />
                    </van-dropdown-menu>
                  </van-col>
                  <van-col span="8">
                    <van-stepper
                      v-model="item.count"
                      style="padding: 10px 16px"
                      theme="round"
                      button-size="18"
                      disable-input
                      :min="0"
                      :max="9999"
                      @plus="changeProductCount(1, item)"
                      @minus="changeProductCount(-1, item)"
                    />
                  </van-col>
                </van-row>
              </van-cell>
            </div>
          </van-index-bar>
        </van-list>
        <van-empty v-else description="暂无数据"/>
      </van-tab>
    </van-tabs>
  </div>
</template>

<script>
import {mapState, mapMutations} from 'vuex'
import {listProduct} from '@/api/product'

export default {
  data() {
    return {
      active: 0,
      keyword: '',
      productList: [],
      productShowGroup: {},
      unitMap: {
        1: [
          {text: '大包', value: 1},
          {text: '小包', value: 2},
          {text: '件', value: 3},
          {text: '斤', value: 4}
        ],
        2: [
          {text: '市斤', value: 1},
          {text: '公斤 ', value: 2},
          {text: '件', value: 3}
        ],
        3: [
          {text: '盒', value: 1},
          {text: '市斤', value: 2},
          {text: '公斤 ', value: 3},
          {text: '件', value: 4}
        ]
      },
      indexList: []
    }
  },
  computed: {
    ...mapState({
      cacheVegetableList: 'cacheVegetableList',
      cacheSoyList: 'cacheSoyList'
    })
  },
  mounted() {
    if (this.cacheVegetableList.length) {
      this.handleData(this.cacheVegetableList)
      this.productList = this.cacheVegetableList
    } else {
      listProduct(1).then(({rows}) => {
        rows.forEach(item => {
          item.num = 10
          item.unit = 1
          item.count = 0
        })
        this.productList = rows
        this.handleData(this.productList)
      })
    }
  },
  watch: {
    keyword() {
      const reg = new RegExp(this.keyword),
        arr = []
      this.productList.forEach(item => reg.test(item.name) && arr.push(item))
      this.handleData(arr)
    },
    async active() {
      if (this.active === 0) {
        if (!this.cacheVegetableList.length) {
          await listProduct(1).then(({rows}) => {
            rows.forEach(item => {
              item.num = 10
              item.unit = 1
              item.count = 0
            })
            this.setCacheVegetableList(rows)
          })
        }
        this.setCacheSoyList(this.productList)
        this.productList = this.cacheVegetableList
      } else {
        if (!this.cacheSoyList.length) {
          await listProduct(2).then(({rows}) => {
            rows.forEach(item => {
              item.num = 10
              item.unit = 1
              item.count = 0
            })
            this.setCacheSoyList(rows)
          })
        }
        this.setCacheVegetableList(this.productList)
        this.productList = this.cacheSoyList
      }
      this.keyword = ''
      this.handleData(this.productList)
    }
  },
  methods: {
    ...mapMutations({
      addProduct: 'addProduct',
      removeProduct: 'removeProduct',
      setCacheVegetableList: 'setCacheVegetableList',
      setCacheSoyList: 'setCacheSoyList'
    }),
    handleData(originList) {
      this.productShowGroup = {}
      for (const item of originList) {
        const index = item.productIndex
        let list = this.productShowGroup[index]
        if (!list) {
          list = []
          this.productShowGroup[index] = list
        }
        list.push(item)
      }
      this.indexList = Object.keys(this.productShowGroup)
    },
    changeProductCount(value, data) {
      if (value === 1) {
        this.addProduct(data)
      } else {
        this.removeProduct(data)
      }
      if (this.active === 0) {
        this.setCacheVegetableList(this.productList)
      } else {
        this.setCacheSoyList(this.productList)
      }
    }
  }
}
</script>

<style scoped lang="less">
::v-deep .van-dropdown-menu__bar {
  box-shadow: none;
}

::v-deep .van-cell__value--alone {
  text-align: center;
}

::v-deep .van-field__control {
  text-align: center;
}

.num-filed {
  border-bottom: solid 1px #dcdee0;
}
</style>
