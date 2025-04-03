/*
 * @Author: 赖宇凡  happystarrccat@gmail.com
 * @Date: 2025-04-02 16:03:46
 * @LastEditors: 赖宇凡  happystarrccat@gmail.com
 * @LastEditTime: 2025-04-02 16:05:35
 * @FilePath: \react-ui-master\src\pages\list\card.js
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
import React, { useState, useEffect } from "react";
import { getRecommendList ,createTrades} from '@/api'
import { Card, Avatar, Row, Col, Typography, Modal, Form, Input, message } from "antd";
import MyIcon from "@/components/icon";
import "./index.less";
import * as echarts from 'echarts'
import { useStateUserInfo } from "@/store/hooks";


function useCardPage() {
    const [dataList, setList] = useState([]);
    // console.log(list)
    const [showModal, setShow] = useState(false);
    const userInfo = useStateUserInfo()

    const [form] = Form.useForm();
    useEffect(() => {
        getRecommendList(userInfo.clientId).then((res) => {
            console.log("res", res)
            setList(res.data)
        })
    }, []);


    const show = () => {
        setShow(true);
    };
    const hide = () => {
        setShow(false);
    };
    const addList = () => {
        form.validateFields().then((values) => {
            setList([...dataList, values]);
            form.resetFields();
            hide();
        });
    };
    return { show, dataList, showModal, hide, addList, form };
}

// 定义不同类型资产的颜色
const assetColors = {
    'JiJin': '#FF6A6A',
    'GuPiao': '#87CEFF',
    'LiCai': '#32CD32'
};

function echartsShow(dataList) {
    console.log("渲染组件接受", dataList)
    const contentList = [];
    if (dataList.length > 0 && Array.isArray(dataList)) {

        dataList.forEach((item) => {
            contentList.push({
                'tab1':
                    <div>
                        <div className="echartsPie" id={item.recommendationId}></div>
                        <div className="percentShow">
                            <p>收益率：{item.yieldRate} {getReturnRateIcon(item.yieldRate)}</p>
                            <p>风险率：{item.riskLevel} {getRiskRateIcon(item.riskLevel)}</p>
                        </div>
                    </div>
            });
        });

        setTimeout(() => {
            dataList.forEach((item, index) => {
                const dataArr = [];
                if (Array.isArray(item.recommendedItems) && item.recommendedItems.length > 0) {
                    item.recommendedItems.forEach((subItem, index) => {
                        // productPercent.forEach((subItem) => {
                        dataArr.push({
                            value: subItem.productPercent,
                            name: subItem.productName,
                            itemStyle: {
                                color: assetColors[index]
                            }
                        });
                        // });
                    });
                }
                if (document.getElementById(item.recommendationId)) {
                    const myPie = echarts.init(document.getElementById(item.recommendationId));
                    myPie.setOption({
                        series: [
                            {
                                type: 'pie',
                                data: dataArr,
                                // 设置饼图的内外半径，缩小饼图比例
                                radius: ['30%', '50%'],
                                label: {
                                    show: true,
                                    position: 'outside'
                                },
                                labelLine: {
                                    // 缩短第一段线的长度
                                    length: 1,
                                    // 缩短第二段线的长度
                                    length2: 3
                                }
                            }
                        ]
                    });
                }

            });


        }, 500);
    }

    return contentList;
}

function getReturnRateIcon(rate) {
    if (rate > 0.7) {
        return <span className="high-return" style={{ backgroundColor: 'green', color: 'white', padding: '2px 4px', borderRadius: '2px' }}>高收益</span>;
    }
    return null;
}

function getRiskRateIcon(rate) {
    if (rate > 0.7) {
        return <span className="high-risk" style={{ backgroundColor: 'red', color: 'white', padding: '2px 4px', borderRadius: '2px' }}>高风险</span>;
    }
    return null;
}

export default function CardPage() {
    const { show, showModal, addList, dataList, hide, form } = useCardPage();
    const [activeTabKey1, setActiveTabKey1] = useState('tab1');
    const [contentList, setContentList] = useState([]);


    useEffect(() => {
        const newContentList = echartsShow(dataList);
        setContentList(newContentList);
    }, [dataList]);

    const onTab1Change = key => {
        setActiveTabKey1(key);
    };

    const handleBuyConfirm = (item) => {
        Modal.confirm({
            title: '确认购买',
            content: `是否确认购买 ${item.recommendationName}？`,
            onOk() {
                createTrades(item.recommendationId).then((res) => {
                  if(res.code==0){
                    message.success('购买成功');
                  }else if(res.code==1){
                    message.success('购买失败，联系管理员哦~');
                  }
                }).catch(err=>{
                    message.error('购买失败，联系管理员哦~');

                })
            },
            onCancel() {
                message.info('已取消购买');
            },
        });
    };

    return (
        <div className="card-container">
            <Row gutter={[16, 16]}>
                {dataList.map((item, index) => (
                    <Col span={7} key={item.recommendationId}>
                        <Card
                            hoverable
                            actions={[
                                <MyIcon type="icon_cancel" className="icon" />,
                                <MyIcon type="icon_selection" className="icon" onClick={() => handleBuyConfirm(item)} />,
                            ]}
                            title={item.recommendationName}
                            style={{ width: '100%' }}
                            tabList={
                                [
                                    {
                                        key: 'tab1',
                                        tab: '详情',
                                    }
                                ]
                            }
                            activeTabKey={activeTabKey1}
                            onTabChange={onTab1Change}
                        >
                            {contentList[index] && contentList[index][activeTabKey1]}
                        </Card>
                    </Col>
                ))}
            </Row>
        </div>
    );
}
CardPage.route = { path: "/card" }

