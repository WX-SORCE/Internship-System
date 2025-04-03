/*
 * @Author: 赖宇凡  happystarrccat@gmail.com
 * @Date: 2025-04-02 16:03:46
 * @LastEditors: yanderraX 15605367+yanderrax@user.noreply.gitee.com
 * @LastEditTime: 2025-04-02 17:43:56
 * @FilePath: \react-ui-master\src\pages\details\person.js
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
import React, { useState } from "react";
import { Card, Tabs, Row, Col, List, Avatar, Badge, notification } from "antd";
import MyIcon from "@/components/icon";
import "./index.less";
import { useStateUserInfo } from "@/store/hooks";


const { TabPane } = Tabs;
const { Meta } = Card;

// 这里写 tab 栏的标题
const tabTitleArr = ["系统通知", "由衷建议"];

// 通过点击事件，点击 tab栏 切换 List
const changeList = (index) => {
  console.log("点击的Tab索引:", index);
};

/**
 * 这里填写从后端获取到的数据，这里为数组对象
 */
let listDataArr = [
  {
    notification_id: "20250325",
    client_id: "客户",
    type: "待审批",
    content: "这里是正文这里是正文这里是正文这里是正文",
    read: 0,
    created_at: "2023-05-15 14:30:00",
  },
  {
    notification_id: "20250325",
    client_id: "客户",
    type: "待审批",
    content: "这里是正文这里是正文这里是正文这里是正文这里是正文这里是正文这里是正文这里是正文",
    read: 0,
    created_at: "2023-05-15 14:30:00",
  },
  {
    notification_id: "20250325",
    client_id: "客户",
    type: "待审批",
    content: "这里是正文这里是正文这里是正文这里是正文这里是正文这里是正文这里是正文这里是正文这里是正文这里是正文这里是正文这里是正文这里是正文这里是正文这里是正文这里是正文这里是正文",
    read: 1,
    created_at: "2023-05-15 14:30:00",
  },
];

let heartfeltAdvice = [];

export default function Person() {
  // 将右上角用户名和首页同步
  const userInfo = useStateUserInfo();

  // 使用 useState 管理 read_count 状态
  const [read_count, setReadCount] = useState(() => {
    let count = 0;
    for (let i = 0; i < listDataArr.length; i++) {
      if (listDataArr[i].read === 0) {
        count++;
      }
    }
    return count;
  });

  // 点击事件
  // 更改 已读 || 未读 状态
  function changeReadState(index) {
    const value = listDataArr[index];
    if (value.read) {
      console.log("已读");
      return;
    }
    console.log("未读");
    // 更新数据中的 read 状态
    listDataArr[index].read = 1;
    // 更新 read_count 状态
    setReadCount(prevCount => prevCount - 1);

    const btnArr = document.querySelectorAll('.readBtn');
    for (let i = 0; i < btnArr.length; i++) {
      if (index === i) {
        console.log('选中');
        btnArr[i].classList.add('readBtn-ed');
        btnArr[i].innerHTML = '已读';
      }
    }
  }

  const tabpanes = Array.from({ length: tabTitleArr.length }, (v, k) => (
    <TabPane
      tab={<span onClick={() => changeList(k)}>{tabTitleArr[k]}</span>}
      key={k + 1 + ""}
    >
      <List
        itemLayout="vertical"
        size="large"
        header={<Badge count={k === 0? read_count : 0} size="small"><h2>{tabTitleArr[k]}</h2></Badge>}
        dataSource={k === 0? listDataArr : heartfeltAdvice}
        renderItem={(item, index) => (
          <List.Item key={item.title}>
            <List.Item.Meta
              title={'通知编号：' + item.notification_id + ' ' + item.type}
              description={item.created_at}
            />
            {item.content}
            <div className="btnDiv"><button className={listDataArr[index].read === 0? 'readBtn' : 'readBtn-ed'} onClick={() => changeReadState(index)}>{listDataArr[index].read === 0? '未读' : '已读'}</button></div>
          </List.Item>
        )}
      />
    </TabPane>
  ));

  return (
    <div className="person-container">
      <Row>
        <Col span={6}>
          <Card
            cover={
              <img
                alt="example"
                src="https://avatars.githubusercontent.com/u/56569970?v=4"
              />
            }
          >
            <Meta title={userInfo.username} />
            <div className="info">
              <p>
                <MyIcon type="icon_infopersonal" className="icon" />
                {userInfo.gender? userInfo.gender : "男"}
              </p>
              <p>
                <MyIcon type="icon_address1" className="icon" />
                {userInfo.nationality? userInfo.nationality : "中国"}
              </p>
              <p>
                <MyIcon type="icon_edit" className="icon" />
                <a
                  href="https://github.com/kongyijilafumi/"
                  target="_blank"
                  rel="noreferrer"
                >
                  {userInfo.email? userInfo.email : "邮箱地址"}
                </a>
              </p>
            </div>
          </Card>
        </Col>
        <Col span={17} offset={1} className="tabs">
          <Tabs defaultActiveKey="1">{tabpanes}</Tabs>
        </Col>
      </Row>
    </div>
  );
}
Person.route = { path: "/details" };