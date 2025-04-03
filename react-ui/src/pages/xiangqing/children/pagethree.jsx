import React, { useState } from "react";
import { Descriptions } from "antd";
import {getDashboard} from '@/api/index'
import axios from "axios";

function Tabpanesthree() {
    const [descriptions] = useState({
        Name: '444',
        phone: '113424444',
        danwei: '中软',
        touzidengji: 'A',
        QQ: '444',
        weixin: '13231',
    });

    const renderDescriptionItem = (label, value) => (
        <Descriptions.Item
            label={label}
            labelStyle={{
                fontWeight: "bold",
                color: "black",
            }}
            className="my--content"
        >
            {value}
        </Descriptions.Item>
    );

    return (
        <div className="labekimport">
            <span className="spanstylrc">基础信息</span>
            <Descriptions column={3} bordered>
                {renderDescriptionItem("姓名", descriptions.Name)}
                {renderDescriptionItem("电话", descriptions.phone)}
                {renderDescriptionItem("单位", descriptions.danwei)}
                {renderDescriptionItem("管理单位", descriptions.touzidengji)}
                {renderDescriptionItem("调度机构", descriptions.QQ)}
                {renderDescriptionItem("行政地区", descriptions.weixin)}
            </Descriptions>
            <span className="spanstylrc">基础信息</span>
            <Descriptions column={3} bordered>
                {renderDescriptionItem("姓名", descriptions.Name)}
                {renderDescriptionItem("电话", descriptions.phone)}
                {renderDescriptionItem("单位", descriptions.danwei)}
                {renderDescriptionItem("管理单位", descriptions.touzidengji)}
                {renderDescriptionItem("调度机构", descriptions.QQ)}
                {renderDescriptionItem("行政地区", descriptions.weixin)}
            </Descriptions><span className="spanstylrc">基础信息</span>
            <Descriptions column={3} bordered>
                {renderDescriptionItem("姓名", descriptions.Name)}
                {renderDescriptionItem("电话", descriptions.phone)}
                {renderDescriptionItem("单位", descriptions.danwei)}
                {renderDescriptionItem("管理单位", descriptions.touzidengji)}
                {renderDescriptionItem("调度机构", descriptions.QQ)}
                {renderDescriptionItem("行政地区", descriptions.weixin)}
            </Descriptions>
        </div>
    );
};



export default Tabpanesthree;