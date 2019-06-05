import React, { Component } from "react";
import { Row, Col, Input, Select, Button, } from "antd";
import { getallphongban, getallnhanvien } from "../../../Services/apimanh1";
import { banGiaoThietBi } from "../../../Services/apimanh";
import history from '../../../Utils/history'

import { element } from "prop-types";
import List2 from "./List";
import { connect } from "react-redux";

const Option = Select.Option;

class bangiao1 extends Component {
  constructor(props) {
    super(props);
    this.state = {
      phongBan: [],
      nhanVien: [],
      maPhongBan: '',
      kieuBanGiao: false,
      maNhanVien: ''
    };
  }
  callapi = async () => {
    let phongBan = await getallphongban();
    let nhanVien = await getallnhanvien();
    this.setState({
      phongBan: phongBan,
      nhanVien: nhanVien
    });
  };
  componentDidMount() {
    if (this.props.lstBanGiao.lst2.length === 0) {
      history.push('/app/kcbangiao');
    }
    this.callapi();
  }

  handleChangepb = value => {
    this.setState({
      maPhongBan: value
    });
  };

  handleChangeKieuBanGiao = value => {
    this.setState({
      kieuBanGiao: value
    });
  }

  handleChangenv = nv => {
    this.setState({
      maNhanVien: nv
    });
  };

  getListThietBi = () => {
    var lstThietBi = [];
    for (var element of this.props.lstBanGiao.lst2) {
      for (var tb of element.lstThietBi) {
        lstThietBi = [...lstThietBi, tb];
      }
    }
    return lstThietBi;
  }

  onHandleClickSubmit1 = async () => {
    var b = window.confirm("Bạn có muốn hoàn tất");
    if (b == true) {
      var objectToServer = {
        kieuBanGiao: this.state.kieuBanGiao,
        maPhongBanNhan: this.state.maPhongBan,
        maNhanVien: this.state.maNhanVien,
        lstThietBi: this.getListThietBi()

      }
      let res = banGiaoThietBi(objectToServer);
      console.log(objectToServer);
    }
  };

  render() {
    const { thietbiLoai } = this.props;
    console.log(thietbiLoai);
    const { phongBan, nhanVien } = this.state;
    return (
      <div>
        <Row>
          <Col
            xs={12}
            sm={12}
            md={12}
            lg={12}
            xl={12}
          >
            <div>
              <h3 style={{ textAlign: 'center' }}>DANH SÁCH THIẾT BỊ BÀN GIAO</h3>

              <List2 handleClickSelect={this.changeDataInListUnSelect} lableClick="Bỏ chọn" data={this.props.lstBanGiao.lst2} />

            </div>
          </Col>

          <Col
            xs={12}
            sm={12}
            md={12}
            lg={12}
            xl={12}
          >
            <h3 style={{ textAlign: 'center' }}>THÔNG TIN BÀN GIAO</h3>
            <div />
            <div style={{ backgroundColor: '#f1f1f1', paddingTop: '10px' }}>

              <div className="ant-row ant-form-item">
                <div className="ant-form-item-label ant-col-xs-24 ant-col-sm-7">
                  <label htmlFor="tenTinhTrang" className="ant-form-item-required" title="Kiểu bàn giao">Kiểu bàn giao</label>
                </div>
                <div className="ant-form-item-control-wrapper ant-col-xs-24 ant-col-sm-16">
                  <div className="ant-form-item-control">
                    <span className="ant-form-item-children">
                      <Select
                        defaultValue="Chọn kiểu bàn giao"
                        style={{ width: 300 }}
                        onChange={this.handleChangeKieuBanGiao}
                      >
                        <Option value="ca_nhan">Cá nhân</Option>
                        <Option value="don_vi">Đơn vị</Option>
                      </Select>
                    </span>
                  </div>
                </div>
              </div>

              <div className="ant-row ant-form-item">
                <div className="ant-form-item-label ant-col-xs-24 ant-col-sm-7">
                  <label htmlFor="tenTinhTrang" className="ant-form-item-required" title="Phòng ban nhận">Phòng ban nhận</label>
                </div>
                <div className="ant-form-item-control-wrapper ant-col-xs-24 ant-col-sm-16">
                  <div className="ant-form-item-control">
                    <span className="ant-form-item-children">
                      <Select
                        defaultValue="Chọn phòng ban"
                        style={{ width: 300 }}
                        onChange={this.handleChangepb}
                      >
                        {phongBan.map(element => (
                          <Option key={element.maPhongBan} value={element.maPhongBan}>
                            {element.tenPhongBan}
                          </Option>
                        ))}
                      </Select>
                    </span>
                  </div>
                </div>
              </div>

              <div className="ant-row ant-form-item">
                <div className="ant-form-item-label ant-col-xs-24 ant-col-sm-7">
                  <label htmlFor="tenTinhTrang" className="ant-form-item-required" title="Nhân viên nhận bàn giao">Nhân viên nhận bàn giao</label>
                </div>
                <div className="ant-form-item-control-wrapper ant-col-xs-24 ant-col-sm-16">
                  <div className="ant-form-item-control">
                    <span className="ant-form-item-children">
                      <Select
                        defaultValue="Chọn Nhân viên"
                        style={{ width: 300 }}
                        onChange={this.handleChangenv}
                      >
                        {nhanVien.map(element => (
                          <Option key={element.maNhanVien} value={element.maNhanVien}>
                            {element.tenNhanVien}
                          </Option>
                        ))}
                      </Select>
                    </span>
                  </div>
                </div>
              </div>

              <div className="ant-row ant-form-item">
                <div className="ant-form-item-label ant-col-xs-24 ant-col-sm-7">

                </div>
                <div className="ant-form-item-control-wrapper ant-col-xs-24 ant-col-sm-16" >
                  <div className="ant-form-item-control" >
                    <span className="ant-form-item-children">
                      <Button
                        onClick={this.onHandleClickSubmit1}
                        type="primary"
                      >
                        Hoàn tất bàn giao
                      </Button>
                    </span>
                  </div>
                </div>
              </div>

            </div>
          </Col>
        </Row>
      </div>
    );
  }
}

export default connect(function (state) {
  return {
    lstBanGiao: state.lstBanGiao
  }
})(bangiao1);
