/*
 * @Author: 严西娟 499820911@qq.com
 * @Date: 2025-04-02 15:46:07
 * @LastEditors: 严西娟 499820911@qq.com
 * @LastEditTime: 2025-04-02 15:52:08
 * @FilePath: \react-ant-admin\src\pages\xiangqing\detail.jsx
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
import React from "react";
import { Tabs, Row, Col } from "antd";
import "./index.less";
import Tabpanesone from "@/pages/xiangqing/children/pageone.jsx";
import Tabpanestwo from "@/pages/xiangqing/children/pagetwo.jsx";
import Tabpanesthree from "@/pages/xiangqing/children/pagethree.jsx";

const { TabPane } = Tabs;

const Person = () => {


    const tabTitleArr = ['基本信息', '组合', "推荐历史"];

    const changeList = (index) => {
        console.log('点击的 Tab 索引:', index);
    };

    const tabpanes = Array.from({ length: tabTitleArr.length }, (v, k) => {
        if (k === 1) {
            return (
                <TabPane
                    tab={<span onClick={() => changeList(k)}>{tabTitleArr[k]}</span>}
                    key={(k + 1).toString()}
                >
                    <Tabpanesone />
                </TabPane>
            );
        } else if (k === 2) {
            return (
                <TabPane
                    tab={<span onClick={() => changeList(k)}>{tabTitleArr[k]}</span>}
                    key={(k + 1).toString()}
                >
                    <Tabpanestwo />
                </TabPane>
            );
        } else {
            return (
                <TabPane
                    tab={<span onClick={() => changeList(k)}>{tabTitleArr[k]}</span>}
                    key={(k + 1).toString()}
                >
                    <Tabpanesthree />
                </TabPane>
            );
        }
    });

    return (
        <div className="person-container">
            <Row>
                <Col span={24} className="tabs">
                    <Tabs defaultActiveKey="1">{tabpanes}</Tabs>
                </Col>
            </Row>
        </div>
    );
};

Person.route = { path: "/detail" };

export default Person;