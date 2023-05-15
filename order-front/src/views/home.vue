<template>
  <div id="app">
    <van-search v-model="keyword" placeholder="搜索" />
    <van-tabs v-model="active" :sticky="true">
      <van-tab v-for="tab in ['蔬菜', '豆制品']" :title="tab" :key="tab">
        <van-list v-if="indexList.length">
          <van-index-bar :index-list="indexList">
            <div v-for="anchor in indexList" :key="anchor">
              <van-index-anchor :index="anchor" style="text-align: left">{{
                anchor
              }}</van-index-anchor>
              <van-cell
                v-for="(item, index) in productShowGroup[anchor]"
                :key="index"
              >
                <van-row>
                  <van-col span="6">
                    <van-field :value="item.name" readonly />
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
        <van-empty v-else description="暂无数据" />
      </van-tab>
    </van-tabs>
  </div>
</template>

<script>
import { mapState, mapMutations } from 'vuex'

export default {
  data() {
    return {
      active: 0,
      keyword: '',
      productList: [
        {
          id: 1,
          index: 'B',
          name: '百合',
          num: 10,
          unitKey: '1',
          unit: 1,
          count: 0
        },
        {
          id: 2,
          index: 'C',
          name: '菜花',
          num: 10,
          unitKey: '2',
          unit: 1,
          count: 0
        },
        {
          id: 3,
          index: 'C',
          name: '菜心',
          num: 10,
          unitKey: '2',
          unit: 1,
          count: 0
        },
        {
          id: 4,
          index: 'C',
          name: '长豆角',
          num: 10,
          unitKey: '2',
          unit: 1,
          count: 0
        },
        {
          id: 5,
          index: 'C',
          name: '长南瓜',
          num: 10,
          unitKey: '2',
          unit: 1,
          count: 0
        },
        {
          id: 6,
          index: 'C',
          name: '长茄子',
          num: 10,
          unitKey: '2',
          unit: 1,
          count: 0
        },
        {
          id: 7,
          index: 'C',
          name: '朝天椒',
          num: 10,
          unitKey: '2',
          unit: 1,
          count: 0
        },
        {
          id: 8,
          index: 'D',
          name: '大白菜',
          num: 10,
          unitKey: '2',
          unit: 1,
          count: 0
        },
        {
          id: 9,
          index: 'D',
          name: '大葱',
          num: 10,
          unitKey: '2',
          unit: 1,
          count: 0
        },
        {
          id: 10,
          index: 'D',
          name: '地瓜',
          num: 10,
          unitKey: '2',
          unit: 1,
          count: 0
        },
        {
          id: 11,
          index: 'D',
          name: '冬瓜',
          num: 10,
          unitKey: '2',
          unit: 1,
          count: 0
        },
        {
          id: 12,
          index: 'D',
          name: '豆角',
          num: 10,
          unitKey: '2',
          unit: 1,
          count: 0
        },
        {
          id: 13,
          index: 'D',
          name: '大油菜',
          num: 10,
          unitKey: '2',
          unit: 1,
          count: 0
        },
        {
          id: 14,
          index: 'F',
          name: '法香',
          num: 10,
          unitKey: '2',
          unit: 1,
          count: 0
        },
        {
          id: 15,
          index: 'F',
          name: '凤尾菇',
          num: 10,
          unitKey: '2',
          unit: 1,
          count: 0
        },
        {
          id: 16,
          index: 'G',
          name: '广东菜心',
          num: 10,
          unitKey: '2',
          unit: 1,
          count: 0
        },
        {
          id: 17,
          index: 'G',
          name: '甘蓝',
          num: 10,
          unitKey: '2',
          unit: 1,
          count: 0
        }
      ],
      productShowGroup: {},
      unitMap: {
        1: [
          { text: '大包', value: 1 },
          { text: '小包', value: 2 },
          { text: '件', value: 3 },
          { text: '斤', value: 4 }
        ],
        2: [
          { text: '市斤', value: 1 },
          { text: '公斤 ', value: 2 },
          { text: '件', value: 3 }
        ],
        3: [
          { text: '盒', value: 1 },
          { text: '市斤', value: 2 },
          { text: '公斤 ', value: 3 },
          { text: '件', value: 4 }
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
      this.handleData(this.productList)
    }
  },
  watch: {
    keyword() {
      const reg = new RegExp(this.keyword),
        arr = []
      this.productList.forEach(item => reg.test(item.name) && arr.push(item))
      this.handleData(arr)
    },
    active() {
      if (this.active === 0) {
        if (!this.cacheVegetableList.length) {
          const list =  [
            {
              id: 1,
              index: 'B',
              name: '百合',
              num: 10,
              unitKey: '1',
              unit: 1,
              count: 0
            },
            {
              id: 2,
              index: 'C',
              name: '菜花',
              num: 10,
              unitKey: '2',
              unit: 1,
              count: 0
            },
            {
              id: 3,
              index: 'C',
              name: '菜心',
              num: 10,
              unitKey: '2',
              unit: 1,
              count: 0
            },
            {
              id: 4,
              index: 'C',
              name: '长豆角',
              num: 10,
              unitKey: '2',
              unit: 1,
              count: 0
            },
            {
              id: 5,
              index: 'C',
              name: '长南瓜',
              num: 10,
              unitKey: '2',
              unit: 1,
              count: 0
            },
            {
              id: 6,
              index: 'C',
              name: '长茄子',
              num: 10,
              unitKey: '2',
              unit: 1,
              count: 0
            },
            {
              id: 7,
              index: 'C',
              name: '朝天椒',
              num: 10,
              unitKey: '2',
              unit: 1,
              count: 0
            },
            {
              id: 8,
              index: 'D',
              name: '大白菜',
              num: 10,
              unitKey: '2',
              unit: 1,
              count: 0
            },
            {
              id: 9,
              index: 'D',
              name: '大葱',
              num: 10,
              unitKey: '2',
              unit: 1,
              count: 0
            },
            {
              id: 10,
              index: 'D',
              name: '地瓜',
              num: 10,
              unitKey: '2',
              unit: 1,
              count: 0
            },
            {
              id: 11,
              index: 'D',
              name: '冬瓜',
              num: 10,
              unitKey: '2',
              unit: 1,
              count: 0
            },
            {
              id: 12,
              index: 'D',
              name: '豆角',
              num: 10,
              unitKey: '2',
              unit: 1,
              count: 0
            },
            {
              id: 13,
              index: 'D',
              name: '大油菜',
              num: 10,
              unitKey: '2',
              unit: 1,
              count: 0
            },
            {
              id: 14,
              index: 'F',
              name: '法香',
              num: 10,
              unitKey: '2',
              unit: 1,
              count: 0
            },
            {
              id: 15,
              index: 'F',
              name: '凤尾菇',
              num: 10,
              unitKey: '2',
              unit: 1,
              count: 0
            },
            {
              id: 16,
              index: 'G',
              name: '广东菜心',
              num: 10,
              unitKey: '2',
              unit: 1,
              count: 0
            },
            {
              id: 17,
              index: 'G',
              name: '甘蓝',
              num: 10,
              unitKey: '2',
              unit: 1,
              count: 0
            }
          ]
          this.setCacheVegetableList(list)
        }
        this.setCacheSoyList(this.productList)
        this.productList = this.cacheVegetableList
      } else {
        if (!this.cacheSoyList.length) {
          const list = [
            {
              id: 18,
              index: 'D',
              name: '豆腐',
              num: 10,
              unitKey: '2',
              unit: 1,
              count: 0
            },
            {
              id: 19,
              index: 'D',
              name: '豆腐丝',
              num: 10,
              unitKey: '2',
              unit: 1,
              count: 0
            },
            {
              id: 20,
              index: 'F',
              name: '粉皮',
              num: 10,
              unitKey: '2',
              unit: 1,
              count: 0
            },
            {
              id: 21,
              index: 'H',
              name: '盒豆腐',
              num: 10,
              unitKey: '3',
              unit: 1,
              count: 0
            },
            {
              id: 22,
              index: 'H',
              name: '黄豆芽',
              num: 10,
              unitKey: '2',
              unit: 1,
              count: 0
            },
            {
              id: 23,
              index: 'L',
              name: '老豆腐',
              num: 10,
              unitKey: '3',
              unit: 1,
              count: 0
            },
            {
              id: 24,
              index: 'L',
              name: '绿豆芽',
              num: 10,
              unitKey: '2',
              unit: 1,
              count: 0
            }
          ]
          this.setCacheSoyList(list)
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
        const index = item.index
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
