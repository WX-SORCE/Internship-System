import dayjs from "dayjs";
import { message } from "antd";
import { formatMenu } from "@/utils"
import { getLocalUser } from "@/utils";

let currentUser = {};
const userInfoList = [
  {
    user_id: 1,
    username: "张同学",
    account: "admin",
    type_id: "超级管理员",
    t_id: 1,
  },
  {
    user_id: 2,
    username: "王五",
    account: "user",
    type_id: "用户",
    t_id: 2,
  },
  {
    user_id: 4,
    username: "李四",
    account: "qq123456",
    type_id: "游客",
    t_id: 3,
  },
  {
    user_id: 5,
    username: "路过的老鼠",
    account: "jake",
    type_id: "低权游客",
    t_id: 4,
  },
  {
    user_id: 6,
    username: "站长",
    account: "superAdmin",
    type_id: "超级管理员",
    t_id: 1,
  },
];

// let menu = [
//   {
//     [MENU_TITLE]: "个人中心",
//     [MENU_PATH]: "/details/person",
//     [MENU_KEY]: 1,
//     [MENU_PARENTKEY]: null,
//     [MENU_ICON]: "icon_edit",
//     [MENU_KEEPALIVE]: "false",
//     [MENU_ORDER]: 1,
//   },
//  {
//     [MENU_TITLE]: "客户详情页",
//     [MENU_PATH]: "/list/search",
//     [MENU_KEY]: 11,
//     [MENU_PARENTKEY]: null,
//     [MENU_ICON]: null,
//     [MENU_KEEPALIVE]: "false",
//     [MENU_ORDER]: 9588,
//   },
//   {
//     menu_id: 10,
//     [MENU_TITLE]: "卡片列表",
//     [MENU_PATH]: "/card",
//     [MENU_KEY]: 10,
//     [MENU_PARENTKEY]: 9,
//     [MENU_ICON]: null,
//     [MENU_KEEPALIVE]: "false",
//     [MENU_ORDER]: 5485,
//   },

//   // {
//   //   [MENU_TITLE]: "查询列表",
//   //   [MENU_PATH]: "/search",
//   //   [MENU_KEY]: 11,
//   //   [MENU_PARENTKEY]: 9,
//   //   [MENU_ICON]: null,
//   //   [MENU_KEEPALIVE]: "false",
//   //   [MENU_ORDER]: 9588,
//   // },
//   {
//     [MENU_TITLE]: "表单页",
//     [MENU_PATH]: "/form",
//     [MENU_KEY]: 7,
//     [MENU_PARENTKEY]: null,
//     [MENU_ICON]: "icon_form",
//     [MENU_KEEPALIVE]: "false",
//     [MENU_ORDER]: 3,
//   },
//   {
//     [MENU_TITLE]: "基础表单",
//     [MENU_PATH]: "/index",
//     [MENU_KEY]: 6,
//     [MENU_PARENTKEY]: 7,
//     [MENU_ICON]: null,
//     [MENU_KEEPALIVE]: "false",
//     [MENU_ORDER]: 9654,
//   },
//   // {
//   //   [MENU_TITLE]: "个人中心",
//   //   [MENU_PATH]: "/person",
//   //   [MENU_KEY]: 2,
//   //   [MENU_PARENTKEY]: 1,
//   //   [MENU_ICON]: "icon_infopersonal",
//   //   [MENU_KEEPALIVE]: "false",
//   //   [MENU_ORDER]: 9998,
//   // },
//   {
//     [MENU_TITLE]: "结果页",
//     [MENU_PATH]: "/result",
//     [MENU_KEY]: 16,
//     [MENU_PARENTKEY]: null,
//     [MENU_ICON]: "icon_voiceprint",
//     [MENU_KEEPALIVE]: "false",
//     [MENU_ORDER]: 4,
//   },
//   {
//     [MENU_TITLE]: "403",
//     [MENU_PATH]: "/403",
//     [MENU_KEY]: 3,
//     [MENU_PARENTKEY]: 16,
//     [MENU_ICON]: "icon_locking",
//     [MENU_KEEPALIVE]: "false",
//     [MENU_ORDER]: 0,
//   },
//   {
//     [MENU_TITLE]: "404",
//     [MENU_PATH]: "/404",
//     [MENU_KEY]: 4,
//     [MENU_PARENTKEY]: 16,
//     [MENU_ICON]: "icon_close",
//     [MENU_KEEPALIVE]: "false",
//     [MENU_ORDER]: 1,
//   },
//   {
//     [MENU_TITLE]: "500",
//     [MENU_PATH]: "/500",
//     [MENU_KEY]: 5,
//     [MENU_PARENTKEY]: 16,
//     [MENU_ICON]: "icon_privacy_closed",
//     [MENU_KEEPALIVE]: "false",
//     [MENU_ORDER]: 4568,
//   },
//   {
//     [MENU_TITLE]: "统计",
//     [MENU_PATH]: "/statistics",
//     [MENU_KEY]: 17,
//     [MENU_PARENTKEY]: null,
//     [MENU_ICON]: "icon_MTR",
//     [MENU_KEEPALIVE]: "true",
//     [MENU_ORDER]: 5,
//   },
//   {
//     [MENU_TITLE]: "访客统计",
//     [MENU_PATH]: "/visitor",
//     [MENU_KEY]: 18,
//     [MENU_PARENTKEY]: 17,
//     [MENU_ICON]: "icon_addresslist",
//     [MENU_KEEPALIVE]: "true",
//     [MENU_ORDER]: 1,
//   },
//   {
//     [MENU_TITLE]: "系统管理",
//     [MENU_PATH]: "/power",
//     [MENU_KEY]: 12,
//     [MENU_PARENTKEY]: null,
//     [MENU_ICON]: "icon_set",
//     [MENU_KEEPALIVE]: "false",
//     [MENU_ORDER]: 9,
//   },
//   {
//     [MENU_TITLE]: "权限类别",
//     [MENU_PATH]: "/type",
//     [MENU_KEY]: 14,
//     [MENU_PARENTKEY]: 12,
//     [MENU_ICON]: "icon_safety",
//     [MENU_KEEPALIVE]: "true",
//     [MENU_ORDER]: 12,
//   },
//   {
//     [MENU_TITLE]: "菜单管理",
//     [MENU_PATH]: "/menu",
//     [MENU_KEY]: 13,
//     [MENU_PARENTKEY]: 12,
//     [MENU_ICON]: "icon_menu",
//     [MENU_KEEPALIVE]: "true",
//     [MENU_ORDER]: 1475,
//   },
//   {
//     [MENU_TITLE]: "用户管理",
//     [MENU_PATH]: "/user",
//     [MENU_KEY]: 15,
//     [MENU_PARENTKEY]: 12,
//     [MENU_ICON]: "icon_infopersonal",
//     [MENU_KEEPALIVE]: "true",
//     [MENU_ORDER]: 1593,
//   },
//   {
//     [MENU_TITLE]: "图标库",
//     [MENU_PATH]: "/icons",
//     [MENU_KEY]: 8,
//     [MENU_PARENTKEY]: null,
//     [MENU_ICON]: "icon_pen",
//     [MENU_KEEPALIVE]: "true",
//     [MENU_ORDER]: 10,
//   },

// ];

let menu = [
  {
    menu_id: 1,
    [MENU_TITLE]: "个人中心",
    [MENU_PATH]: "/details",
    [MENU_KEY]: 1,
    [MENU_PARENTKEY]: null,
    [MENU_ICON]: "icon_home",
    [MENU_KEEPALIVE]: "false",
    [MENU_ORDER]: 1,
  },
  {
    menu_id: 2,
    [MENU_TITLE]: "交易列表页",
    [MENU_PATH]: "/trade",
    [MENU_KEY]: 2,
    [MENU_PARENTKEY]: null,
    [MENU_ICON]: "icon_phonebill",
    [MENU_KEEPALIVE]: "false",
    [MENU_ORDER]: 1,
  },
  {
    menu_id: 3,
    [MENU_TITLE]: "客户列表页",
    [MENU_PATH]: "/list/search",
    [MENU_KEY]: 3,
    [MENU_PARENTKEY]: null,
    [MENU_ICON]: "icon_form",
    [MENU_KEEPALIVE]: "false",
    [MENU_ORDER]: 1,
  },
  {
    menu_id: 4,
    [MENU_TITLE]: "客户详情页",
    [MENU_PATH]: "/detail",
    [MENU_KEY]: 4,
    [MENU_PARENTKEY]: null,
    [MENU_ICON]: "icon_voiceprint",
    [MENU_KEEPALIVE]: "false",
    [MENU_ORDER]: 0,
  },
  {
    menu_id: 5,
    [MENU_TITLE]: "推荐服务列表",
    [MENU_PATH]: "/card",
    [MENU_KEY]: 5,
    [MENU_PARENTKEY]: null,
    [MENU_ICON]: 'icon_collection',
    [MENU_KEEPALIVE]: "false",
    [MENU_ORDER]: 1,
  },
  {
    menu_id: 6,
    [MENU_TITLE]: "投资组合⻚",
    [MENU_PATH]: "/board",
    [MENU_KEY]: 6,
    [MENU_PARENTKEY]: null,
    [MENU_ICON]: "icon_infopersonal",
    [MENU_KEEPALIVE]: "false",
    [MENU_ORDER]: 1,
  },
  {
    menu_id: 7,
    [MENU_TITLE]: "审批页",
    [MENU_PATH]: "/list/adit",
    [MENU_KEY]: 7,
    [MENU_PARENTKEY]: null,
    [MENU_ICON]: "icon_guarantee",
    [MENU_KEEPALIVE]: "false",
    [MENU_ORDER]: 1,
  },
  {
    menu_id: 8,
    [MENU_TITLE]: "消息通知",
    [MENU_PATH]: "/websocket",
    [MENU_KEY]: 8,
    [MENU_PARENTKEY]: null,
    [MENU_ICON]: "icon_guarantee",
    [MENU_KEEPALIVE]: "false",
    [MENU_ORDER]: 1,
  },
  {
    menu_id: 8,
    [MENU_TITLE]: "消息通知",
    [MENU_PATH]: "/websocket",
    [MENU_KEY]: 8,
    [MENU_PARENTKEY]: null,
    [MENU_ICON]: "icon_guarantee",
    [MENU_KEEPALIVE]: "false",
    [MENU_ORDER]: 1,
  }, {
    menu_id: 9,
    [MENU_TITLE]: "风险表单页",
    [MENU_PATH]: "/fengxianfrom",
    [MENU_KEY]: 9,
    [MENU_PARENTKEY]: null,
    [MENU_ICON]: "icon_voiceprint",
    [MENU_KEEPALIVE]: "false",
    [MENU_ORDER]: 1,
  },
  {
    menu_id: 10,
    [MENU_TITLE]: "风险审批页",
    [MENU_PATH]: "/list/aditfengkong",
    [MENU_KEY]: 10,
    [MENU_PARENTKEY]: null,
    [MENU_ICON]: "icon_MTR",
    [MENU_KEEPALIVE]: "false",
    [MENU_ORDER]: 1,
  },
  // {
  //   [MENU_TITLE]: "查询列表",
  //   [MENU_PATH]: "/search",
  //   [MENU_KEY]: 11,
  //   [MENU_PARENTKEY]: 9,
  //   [MENU_ICON]: null,
  //   [MENU_KEEPALIVE]: "false",
  //   [MENU_ORDER]: 9588,
  // },
  // {
  //   [MENU_TITLE]: "表单页",
  //   [MENU_PATH]: "/form",
  //   [MENU_KEY]: 7,
  //   [MENU_PARENTKEY]: null,
  //   [MENU_ICON]: "icon_form",
  //   [MENU_KEEPALIVE]: "false",
  //   [MENU_ORDER]: 3,
  // },
  // {
  //   [MENU_TITLE]: "基础表单",
  //   [MENU_PATH]: "/index",
  //   [MENU_KEY]: 6,
  //   [MENU_PARENTKEY]: 7,
  //   [MENU_ICON]: null,
  //   [MENU_KEEPALIVE]: "false",
  //   [MENU_ORDER]: 9654,
  // },
  // {
  //   [MENU_TITLE]: "基础表单",
  //   [MENU_PATH]: "/index",
  //   [MENU_KEY]: 6,
  //   [MENU_PARENTKEY]: 7,
  //   [MENU_ICON]: null,
  //   [MENU_KEEPALIVE]: "false",
  //   [MENU_ORDER]: 9654,
  // },
  // {
  //   [MENU_TITLE]: "个人中心",
  //   [MENU_PATH]: "/person",
  //   [MENU_KEY]: 2,
  //   [MENU_PARENTKEY]: 1,
  //   [MENU_ICON]: "icon_infopersonal",
  //   [MENU_KEEPALIVE]: "false",
  //   [MENU_ORDER]: 9998,
  // },
  // {
  //   [MENU_TITLE]: "结果页",
  //   [MENU_PATH]: "/result",
  //   [MENU_KEY]: 16,
  //   [MENU_PARENTKEY]: null,
  //   [MENU_ICON]: "icon_voiceprint",
  //   [MENU_KEEPALIVE]: "false",
  //   [MENU_ORDER]: 4,
  // },
  // {
  //   [MENU_TITLE]: "403",
  //   [MENU_PATH]: "/403",
  //   [MENU_KEY]: 3,
  //   [MENU_PARENTKEY]: 16,
  //   [MENU_ICON]: "icon_locking",
  //   [MENU_KEEPALIVE]: "false",
  //   [MENU_ORDER]: 0,
  // },
  // {
  //   [MENU_TITLE]: "404",
  //   [MENU_PATH]: "/404",
  //   [MENU_KEY]: 4,
  //   [MENU_PARENTKEY]: 16,
  //   [MENU_ICON]: "icon_close",
  //   [MENU_KEEPALIVE]: "false",
  //   [MENU_ORDER]: 1,
  // },
  // {
  //   [MENU_TITLE]: "500",
  //   [MENU_PATH]: "/500",
  //   [MENU_KEY]: 5,
  //   [MENU_PARENTKEY]: 16,
  //   [MENU_ICON]: "icon_privacy_closed",
  //   [MENU_KEEPALIVE]: "false",
  //   [MENU_ORDER]: 4568,
  // },
  // {
  //   [MENU_TITLE]: "统计",
  //   [MENU_PATH]: "/statistics",
  //   [MENU_KEY]: 17,
  //   [MENU_PARENTKEY]: null,
  //   [MENU_ICON]: "icon_MTR",
  //   [MENU_KEEPALIVE]: "true",
  //   [MENU_ORDER]: 5,
  // },
  // {
  //   [MENU_TITLE]: "访客统计",
  //   [MENU_PATH]: "/visitor",
  //   [MENU_KEY]: 18,
  //   [MENU_PARENTKEY]: 17,
  //   [MENU_ICON]: "icon_addresslist",
  //   [MENU_KEEPALIVE]: "true",
  //   [MENU_ORDER]: 1,
  // },
  // {
  //   [MENU_TITLE]: "系统管理",
  //   [MENU_PATH]: "/power",
  //   [MENU_KEY]: 12,
  //   [MENU_PARENTKEY]: null,
  //   [MENU_ICON]: "icon_set",
  //   [MENU_KEEPALIVE]: "false",
  //   [MENU_ORDER]: 9,
  // },
  // {
  //   [MENU_TITLE]: "权限类别",
  //   [MENU_PATH]: "/type",
  //   [MENU_KEY]: 14,
  //   [MENU_PARENTKEY]: 12,
  //   [MENU_ICON]: "icon_safety",
  //   [MENU_KEEPALIVE]: "true",
  //   [MENU_ORDER]: 12,
  // },
  // {
  //   [MENU_TITLE]: "菜单管理",
  //   [MENU_PATH]: "/menu",
  //   [MENU_KEY]: 13,
  //   [MENU_PARENTKEY]: 12,
  //   [MENU_ICON]: "icon_menu",
  //   [MENU_KEEPALIVE]: "true",
  //   [MENU_ORDER]: 1475,
  // },
  // {
  //   [MENU_TITLE]: "用户管理",
  //   [MENU_PATH]: "/user",
  //   [MENU_KEY]: 15,
  //   [MENU_PARENTKEY]: 12,
  //   [MENU_ICON]: "icon_infopersonal",
  //   [MENU_KEEPALIVE]: "true",
  //   [MENU_ORDER]: 1593,
  // },
  // {
  //   [MENU_TITLE]: "图标库",
  //   [MENU_PATH]: "/icons",
  //   [MENU_KEY]: 8,
  //   [MENU_PARENTKEY]: null,
  //   [MENU_ICON]: "icon_pen",
  //   [MENU_KEEPALIVE]: "true",
  //   [MENU_ORDER]: 10,
  // },

];
const typeList = [
  {
    type_id: 0,
    name: "风险评估页",
    menu_id: "9",
  },
  {
    type_id: 1,
    name: "客户",
    menu_id: "1,2,5,6",
  },
  { type_id: 2, name: "客户经理", menu_id: "1,3,4,7" },
  { type_id: 3, name: "风控人员", menu_id: "1,7,10" },
  { type_id: 4, name: "合规人员", menu_id: "1,7" },
];
const power = {
  status: 0,
  data: typeList,
  mapKey: [
    { title: "权限id", dataIndex: "type_id", key: "type_id" },
    { title: "权限简称", dataIndex: "name", key: "name" },
    { title: "显示菜单列表id", dataIndex: "menu_id", key: "menu_id" },
  ],
  menu: formatMenu(menu),
};
const userInfo = {
  msg: "登录成功",
  status: 0,
  data: null,
};

const addMenu = {
  msg: "添加成功,菜单栏需要关闭页面重新打开即可生效！",
  status: 0,
};
const addMsg = { msg: "添加成功", status: 0 };

const msgList = [
  {
    m_id: 1,
    name: "AAA",
    email: "1313131@qq.com",
    creator: "超级管理员",
    add_time: "2021-04-20 17:01:09",
  },
  {
    m_id: 2,
    name: "BBB",
    email:
      "123123@gmail.com ",
    creator: "超级管理员",
    add_time: "2021-04-20 17:48:42",
  },
  {
    m_id: 3,
    name: "CCC",
    email:
      "213213@163.com",
    creator: "超级管理员",
    add_time: "2021-04-20 17:46:44",
  },
  {
    m_id: 4,
    name: "DDD",
    email:
      "213213@126.com",
    creator: "超级管理员",
    add_time: "2021-04-20 17:28:45",
  },
];
const msg = {
  status: 0,
  data: {
    mapKey: [
      { title: "消息id", dataIndex: "m_id", key: "m_id" },
      { title: "消息名称", dataIndex: "name", key: "name" },
      { title: "消息描述词", dataIndex: "description", key: "description" },
      { title: "创建人", dataIndex: "creator", key: "creator" },
      { title: "创建时间", dataIndex: "add_time", key: "add_time" },
    ],
    list: msgList,
    total: 4,
  },
  msg: "",
};
const delMenu = { msg: "操作成功", status: 0 };

const MockData = {
  "/getmenu": menu,
  "/getpower": power,
  "/login": userInfo,
  "/addmenu": addMenu,
  "/addmessage": addMsg,
  "/getmessage": msg,
  "/delmenu": delMenu,
  "/getmenuinfo": { status: 0 },
  "/editmenuinfo": { status: 0, msg: "修改成功！" },
  "/getvisitordata": { status: 1, msg: "暂无" },
};

function get(url) {

  return new Promise((res, rej) => {
    setTimeout(() => {
      if (url === "/getmenu") {
        console.log("走了菜单筛选")
        let localInfo = getLocalUser();
        let typeId = localInfo.identityLevel;
        console.log(typeId, "ying")
        
        if (typeId!==null&&typeId!==undefined) {
          let action = typeList.find((i) => i.type_id === typeId)?.menu_id;
          action = action ? action.split(",").map(Number) : [];
          let menuList = menu.filter((i) => action.includes(i[MENU_KEY]));
          console.log(menuList, "菜单处理");
          MockData[url] = menuList;
        }
        res(MockData[url]);
        return;
      }
      res(MockData[url]);
    }, 500);
  }).then((res) => {
    if (res) {
      return res
    } else {
      message.error("接口暂未配置")
      return Promise.reject("接口暂未配置")
    }
  });
}

function post(url, data) {
  return new Promise((res, rej) => {
    setTimeout(() => {
      switch (url) {
        case "/login": {
          const info = userInfoList.find((u) => u.account === data.account);
          if (info) {
            MockData[url].data = info;
            currentUser = info;
            return res(MockData[url]);
          }
          message.error("未找到账号");
          return;
        }
        case "/addmenu": {
          menu.push({ ...data, menu_id: Math.random() });
          return res(MockData[url]);
        }
        case "/addmessage": {
          msgList.push({
            ...data,
            m_id: Math.random(),
            creator: userInfo.data.username,
            add_time: dayjs().format("YYYY-MM-DD HH:mm:ss"),
          });
          msg.data.total = msgList.length;
          return res(MockData[url]);
        }
        case "/delmenu": {
          let newMenu = menu.filter((i) => i[MENU_KEY] !== data[MENU_KEY]);
          menu = newMenu.filter((i) => i[MENU_PARENTKEY] !== data[MENU_KEY]);
          return res(MockData[url]);
        }
        case "/getmenuinfo": {
          MockData[url].data = menu.find((i) => i[MENU_KEY] === data[MENU_KEY]);
          return res(MockData[url]);
        }
        case "/editmenuinfo": {
          menu = menu.map((item) => {
            if (item[MENU_KEY] === data[MENU_KEY]) {
              return { ...item, ...data };
            }
            return item;
          });
          return res(MockData[url]);
        }
        case "/getmessage": {
          let list = [...msgList];
          if (data.name) {
            list = list.filter((i) => i.name.includes(data.name));
          }
          if (data.description) {
            list = list.filter((i) => i.description.includes(data.description));
          }
          MockData[url].data.list = list;
          msg.data.total = list.length;
          return res(MockData[url]);
        }
        default: {
          res({ status: 1, msg: "暂无" });
          break;
        }
      }
    }, 100);
  }).then((res) => {
    if (res.status === 0) {
      return res
    } else {
      message.error("接口暂未配置")
      return Promise.reject("接口暂未配置")
    }
  });
}


const mock = { get, post };

export default mock;
