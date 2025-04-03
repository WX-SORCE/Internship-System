/*
 * @Author: 严西娟 499820911@qq.com
 * @Date: 2025-04-02 15:46:07
 * @LastEditors: 严西娟 499820911@qq.com
 * @LastEditTime: 2025-04-02 15:53:07
 * @FilePath: \react-ant-admin\src\pages\fengxianpage\index.jsx
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
import React, { useEffect, useState } from "react";
import "./index.less";
import {
  Form,
  Input,
  Cascader,
  Select,
  Row,
  Col,
  Checkbox,
  Button,
  AutoComplete,
  message,
  Space,
} from "antd";
import { useHistory } from "react-router-dom";
import { useDispatchLayout } from "@/store/hooks";
import { saveUserimp } from "@/api";
import { useStateUserInfo } from "@/store/hooks";


const { Option } = Select;

function RegistrationForm() {
  const [form] = Form.useForm();
  const history = useHistory();
  const { stateChangeLayout } = useDispatchLayout();
  const [clientIdCHUSHI, setClientId] = useState("");
  const userInfo = useStateUserInfo();

  // 从本地存储获取客户编号
  useEffect(() => {
    const storedClientId = userInfo.clientId;
    console.log("nadaode", storedClientId);
    if (storedClientId) {
      setClientId(storedClientId);
      // 更新表单值
      form.setFieldsValue({
        clientId: storedClientId,
      });
    }
  }, [form, userInfo.clientId]);

  // 定义回调函数
  const onFinish = (values) => {
    // 打印信息
    console.log("Received values of form: ", values);
    saveUserimp(values).then((res) => {
      message.success("提交成功,等待审核中")
    })
  };

  // 中上部弹窗提醒
  useEffect(() => {
    // message.warn("此页面使用了 [NEMU_LAYOUT] 属性 控制布局！")

    return () => {
      stateChangeLayout("pop");
    };
  }, [stateChangeLayout]);

  // 回到上一个页面
  const back = () => {
    history.go(-1);
  };

  return (
    <div>
      <h1 style={{ textAlign: "center", fontSize: "30px" }}>请填写风险评估表单哦~</h1>
      <Form
        form={form}
        onFinish={onFinish}
        name="register"
        className="index-form"
        scrollToFirstError
      >
        <Form.Item
          name="clientId"
          label="客户编号"

        >
          <Input disabled />
        </Form.Item>
        <Form.Item
          name="ege"
          label="年龄"
          rules={[
            {
              required: true,
              message: "请输入年龄!",
            },
          ]}
        >
          <Input />
        </Form.Item>
        <Form.Item
          name="maritalStatus"
          label="婚姻状况"
          rules={[
            {
              required: true,
              message: "请选择婚姻状况!",
            },
          ]}
        >
          <Select>
            <Option value="0">未婚</Option>
            <Option value="1">已婚</Option>
            <Option value="2">离异</Option>
            <Option value="3">丧偶</Option>
          </Select>
        </Form.Item>
        <Form.Item
          name="occupationType"
          label="职业类型"
          rules={[
            {
              required: true,
              message: "请选择职业类型!",
            },
          ]}
        >
          <Select>
            <Option value="0">公务员</Option>
            <Option value="1">国企职工</Option>
            <Option value="2">民企职工</Option>
            <Option value="3">自由职业</Option>
            <Option value="4">其他</Option>
          </Select>
        </Form.Item>
        <Form.Item
          name="totalAssets"
          label="在本行资产总额"
          rules={[
            {
              required: true,
              message: "请输入在本行资产总额!",
            },
          ]}
        >
          <Input />
        </Form.Item>
        <Form.Item
          name="monthlyIncome"
          label="月收入"
          rules={[
            {
              required: true,
              message: "请输入月收入!",
            },
          ]}
        >
          <Input />
        </Form.Item>
        <Form.Item
          name="propertiesOwned"
          label="名下房产数量"
          rules={[
            {
              required: true,
              message: "请输入在名下房产数量!",
            },
          ]}
        >
          <Input />
        </Form.Item>
        <Form.Item
          name="carOwned"
          label="名下车辆数量"
          rules={[
            {
              required: true,
              message: "请输入名下车辆数量!",
            },
          ]}
        >
          <Input />
        </Form.Item>
        <Form.Item
          name="monthlyFixedExpenses"
          label="每月固定支出额"
          rules={[
            {
              required: true,
              message: "请输入每月固定支出额!",
            },
          ]}
        >
          <Input />
        </Form.Item>
        <Form.Item
          name="loanAmount"
          label="贷款额"
          rules={[
            {
              required: true,
              message: "请输入在贷款额!",
            },
          ]}
        >
          <Input />
        </Form.Item>
        <Form.Item
          name="otherDebtAmount"
          label="其它负债额"
          rules={[
            {
              required: true,
              message: "请输入其它负债额!",
            },
          ]}
        >
          <Input />
        </Form.Item>
        <Form.Item
          name="creditLimit"
          label="信用额度"
          rules={[
            {
              required: true,
              message: "请输入在信用额度!",
            },
          ]}
        >
          <Input />
        </Form.Item>
        <Form.Item
          name="pastDueStatus"
          label="是否逾期 "
          rules={[
            {
              required: true,
              message: "请输入是否逾期 !",
            },
          ]}
        >
          <Select>
            <Option value="0">否</Option>
            <Option value="1">是</Option>
          </Select>
        </Form.Item>
        <Form.Item
          name="adverseCreditHistory"
          label="是否不良记录 "
          rules={[
            {
              required: true,
              message: "请输入是否不良记录 !",
            },
          ]}
        >
          <Select>
            <Option value="0">否</Option>
            <Option value="1">是</Option>
          </Select>
        </Form.Item>

        <Form.Item
          name="agreement"
          valuePropName="checked"
          rules={[
            {
              validator: (_, value) =>
                value
                  ? Promise.resolve()
                  : Promise.reject(new Error("请保证信息无误哦")),
            },
          ]}
        >
          <Checkbox>
            我确保以上信息无误
          </Checkbox>
        </Form.Item>
        <Form.Item>
          <Space>
            <Button type="primary" htmlType="submit">
              提交
            </Button>
            {/* <Button onClick={back}>返回上一页</Button> */}
          </Space>
        </Form.Item>
      </Form>
    </div>
  );
}

export default RegistrationForm;

RegistrationForm.route = {
  path: "/fengxianfrom",
};