import React, { Component } from 'react';
import { List, Avatar, Row, Col, Table, Icon, Button, Form, Select } from 'antd';

import { getALlBienNhan, getChiTietByBienNhan } from '../../../Services/api.js';

import moment from 'moment';
import 'moment/locale/vi';
var dateFormat = require('dateformat');

const { Option } = Select;

class DanhSachPhieuBienNhan extends Component {
    constructor(props) {
        super(props);
        this.state = {
            phieus: [],
            phieuChiTiets: [],
            nhanViens: [],
            isLoadding: false,
            maPhieu: '',
            keySelected: '',
            visible: false,   // điều khiển form thêm mới có cho nó hiển thị hay không 
            columns: [{
                title: 'ID',
                render: (text, record) => {
                    return <div>
                        {"CT" + record.maCT}
                    </div>
                },
                key: 'maCT',
            }, {
                title: 'Tên thiết bị',
                dataIndex: 'tenThietBi',
                key: 'tenThietBi',
            }, {
                title: 'Số lượng',
                dataIndex: 'soLuong',
                key: 'soLuong',
            }, {
                title: 'Giá trị/1TB',
                key: 'giaTri',
                render: (text, record) => {
                    return <div>
                        {record.giaTri + " VND"}
                    </div>
                }
            }
            ]
        }
    }

    // dateFormat(new Date(item.ngayBaoTri), "dd/mm/yyyy, h:MM:ss TT")

    getAllPhieu = async () => {
        let phieus = await getALlBienNhan();
        this.setState({ phieus: phieus });
        console.log(phieus)
    }

    componentDidMount() {
        this.getAllPhieu();
    }

    handleClickItem = async (key) => {

        this.setState({ phieuChiTiets: [], isLoadding: true, maPhieu: key, keySelected: key + "__" })
        let data = await getChiTietByBienNhan(key);

        var globle = this;
        setTimeout(function () {
            globle.setState({
                phieuChiTiets: data,
                isLoadding: false
            })
        }, 500)
    }

    //  chuyển đổi ngày---
    convertDay(day) {
        var currentDate;
        if (this.state.isUpdate === true) {
            currentDate = new Date(day);
        } else {
            currentDate = new Date();
        }
        return currentDate;
    }

    render() {
        return (
            <div>
                <Row>
                    <Col span={8} style={{ overflowY: 'scroll', height: 'calc(100vh - 120px)' }}>
                        <List
                            itemLayout="horizontal"
                            dataSource={this.state.phieus}
                            renderItem={item => (
                                // style = { item.tinhTrangPhieu === "cho_sua_chua" ? { background: 'linear-gradient(141deg, #ecdec3c4 0%, #e6d4b2c9 51%, #f3eca8 75%)' } : item.tinhTrangPhieu === 'sua_chua' ? { background: '#f1f1f1' } : { background: 'none' } }
                                <List.Item key={item.maBienNhan} className={item.maBienNhan + "__" === this.state.keySelected ? "item_list_custom_active" : "item_list_custom"} onClick={() => this.handleClickItem(item.maBienNhan)} >
                                    <List.Item.Meta
                                        avatar={<Avatar src="http://localhost:3000/biennhan.png" />}
                                        title={`[PBT${item.maBienNhan}] - ${dateFormat(new Date(item.ngayBienNhan), "dd/mm/yyyy")}`}
                                        description={item.inputtype === "mua-moi" ? "Mua mới từ " + ` - ${item.maNCC}` : item.inputtype === 'duoc-tang' ? "Được tặng bởi " + ` - ${item.maNCC}` : "Được chuyển từ " + ` - ${item.maNCC}`}
                                    />
                                </List.Item>
                            )}
                        />
                    </Col>
                    <Col span={16} style={{ height: '100%', paddingLeft: '10px' }}>
                        {
                            this.state.isLoadding === true ?
                                <div style={{ textAlign: 'center', marginTop: '20px' }}><img height="50px" src="https://ledpanasonic.110.vn/files/product/1132/09-09-2018/blueloadingicon_Co30a4a3.gif" /></div>
                                :
                                <Table dataSource={this.state.phieuChiTiets} columns={this.state.columns} />
                        }

                        {
                            this.state.phieuChiTiets.length > 0 && this.state.phieuChiTiets[0].maTinhTrang === 'TT02' ?
                                <div style={{ textAlign: 'right' }}><Button onClick={() => this.handleClickBatDauBaoTri(this.state.maPhieu)} type="primary">
                                    Bắt đầu sửa chữa<Icon type="right" />
                                </Button></div>
                                :
                                this.state.phieuChiTiets.length > 0 && this.state.phieuChiTiets[0].maTinhTrang === 'TT03' ?
                                    <div style={{ textAlign: 'right' }}><Button type="primary" onClick={this.showModal}>
                                        {/* onClick={() => this.handleClickHoangThanhBaoTri(this.state.maPhieu)} */}
                                        Hoàn thành sửa chữa<Icon type="right" />
                                    </Button></div>
                                    :
                                    ''
                        }
                    </Col>
                </Row>
            </div>
        );
    }
}
export default Form.create()(DanhSachPhieuBienNhan);