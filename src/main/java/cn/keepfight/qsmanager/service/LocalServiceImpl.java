package cn.keepfight.qsmanager.service;

import cn.keepfight.qsmanager.Mapper.*;
import cn.keepfight.qsmanager.model.*;
import cn.keepfight.utils.FXUtils;
import cn.keepfight.utils.QSUtil;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

/**
 * 本地服务实现
 * Created by tom on 2017/6/7.
 */
public class LocalServiceImpl implements ServerService {

    private static SqlSessionFactory factory;

    /**
     * 图片内部存储路径
     */
    private static String picPath;

    static {
        try {
            picPath = new File("./pic").getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public LocalServiceImpl() {
        Reader reader = null;
        try {
            reader = Resources.getResourceAsReader("mybatis.xml");
        } catch (IOException e) {
            // 要是这个也没读出来，那说明没得玩了
            e.printStackTrace();
        }
        factory = new SqlSessionFactoryBuilder().build(reader);
    }

    @Override
    public LoginService getLoginService() {
        return new LoginService() {
            @Override
            public CustomModel login(CustomModel model) throws Exception {
                CustomModel userRes = FXUtils.getMapper(factory, CustomMapper.class, CustomMapper::checkLegality, model);
                if (userRes == null) {
                    throw new PswIllegalException();
                }
                return userRes;
            }

            @Override
            public void checkConnect() throws Exception {

            }
        };
    }

    @Override
    public CustomService getCustomService() {
        return new CustomService() {
            @Override
            public List<CustomModel> selectAll() throws Exception {
                return FXUtils.getMapper(factory, CustomMapper.class, CustomMapper::selectAll);
            }

            @Override
            public CustomModel selectAllByID(Long id) throws Exception {
                return FXUtils.getMapper(factory, CustomMapper.class, CustomMapper::selectAllByID, id);
            }

            @Override
            public void update(CustomModel custom) throws Exception {
                FXUtils.getMapper(factory, CustomMapper.class, CustomMapper::update, custom);
            }

            @Override
            public void insert(CustomModel custom) throws Exception {
                FXUtils.getMapper(factory, CustomMapper.class, CustomMapper::insert, custom);
            }

            @Override
            public void delete(CustomModel custom) throws Exception {
                FXUtils.getMapper(factory, CustomMapper.class, CustomMapper::delete, custom);
            }
        };
    }

    @Override
    public StuffService getStuffService() {
        return new StuffService(){
            @Override
            public List<CustomModel> selectAll() throws Exception {
                return FXUtils.getMapper(factory, CustomMapper.class, CustomMapper::selectAllStuff);
            }

            @Override
            public CustomModel selectAllByID(Long id) throws Exception {
                return FXUtils.getMapper(factory, CustomMapper.class, CustomMapper::selectAllByID, id);
            }

            @Override
            public void update(CustomModel custom) throws Exception {
                FXUtils.getMapper(factory, CustomMapper.class, CustomMapper::update, custom);
            }

            @Override
            public void insert(CustomModel custom) throws Exception {
                FXUtils.getMapper(factory, CustomMapper.class, CustomMapper::insert, custom);
            }

            @Override
            public void delete(CustomModel custom) throws Exception {
                FXUtils.getMapper(factory, CustomMapper.class, CustomMapper::delete, custom);
            }
        };
    }

    @Override
    public ProductService getProductService() {
        return new ProductService() {
            @Override
            public List<ProductModel> selectAll() throws Exception {
                return FXUtils.getMapper(factory, ProductMapper.class, ProductMapper::selectAll);
            }

            @Override
            public void update(ProductModel model) throws Exception {
                FXUtils.getMapper(factory, ProductMapper.class, ProductMapper::update, model);
            }

            @Override
            public void insert(ProductModel model) throws Exception {
                FXUtils.getMapper(factory, ProductMapper.class, ProductMapper::insert, model);
            }

            @Override
            public void delete(ProductModel model) throws Exception {
                FXUtils.getMapper(factory, ProductMapper.class, ProductMapper::delete, model);
            }
        };
    }

    @Override
    public SupplyService getSupplyService() {
        return new SupplyService() {
            @Override
            public List<SupplyModel> selectAll() throws Exception {
                return FXUtils.getMapper(factory, SupplyMapper.class, SupplyMapper::selectAll);
            }

            @Override
            public SupplyModel selectByID(Long ID) throws Exception {
                return FXUtils.getMapper(factory, SupplyMapper.class, SupplyMapper::selectByID, ID);
            }

            @Override
            public void update(SupplyModel model) throws Exception {
                FXUtils.getMapper(factory, SupplyMapper.class, SupplyMapper::update, model);
            }

            @Override
            public void insert(SupplyModel model) throws Exception {
                FXUtils.getMapper(factory, SupplyMapper.class, SupplyMapper::insert, model);
            }

            @Override
            public void delete(SupplyModel model) throws Exception {
                FXUtils.getMapper(factory, SupplyMapper.class, SupplyMapper::delete, model);
            }
        };
    }

    @Override
    public MaterialService getMaterialService() {
        return new MaterialService() {
            @Override
            public List<MaterialModel> selectAll(Long supply) throws Exception {
                return FXUtils.getMapper(factory, MaterialMapper.class, MaterialMapper::selectAll, supply);
            }

            @Override
            public void update(MaterialModel model) throws Exception {
                FXUtils.getMapper(factory, MaterialMapper.class, MaterialMapper::update, model);
            }

            @Override
            public void insert(MaterialModel model) throws Exception {
                FXUtils.getMapper(factory, MaterialMapper.class, MaterialMapper::insert, model);
            }

            @Override
            public void delete(MaterialModel model) throws Exception {
                FXUtils.getMapper(factory, MaterialMapper.class, MaterialMapper::delete, model);
            }
        };
    }

    @Override
    public OrderFavorService getOrderFavorService() {
        return new OrderFavorService(){

            @Override
            public List<ProductModel> selectAll(Long cid) throws Exception {
                return FXUtils.getMapper(factory, OrderFavorMapper.class, OrderFavorMapper::selectAll, cid);
            }

            @Override
            public void insert(long cid, long pid) throws Exception {
                OrderFavorModel model = new OrderFavorModel();
                model.setCid(cid);
                model.setPid(pid);
                FXUtils.getMapper(factory, OrderFavorMapper.class, OrderFavorMapper::insert, model);
            }

            @Override
            public void delete(long cid, long pid) throws Exception {
                OrderFavorModel model = new OrderFavorModel();
                model.setCid(cid);
                model.setPid(pid);
                FXUtils.getMapper(factory, OrderFavorMapper.class, OrderFavorMapper::delete, model);
            }
        };
    }

    @Override
    public ReceiptService getReceiptService() {
        return new ReceiptService() {
            @Override
            public List<ReceiptModelFull> selectAll(Long sid, Long year, Long month) throws Exception {
                ReceiptSelection selection = new ReceiptSelection();
                selection.setSid(sid);
                selection.setYear(year);
                selection.setMonth(month);
                System.out.println(selection);
                List<ReceiptModelFull> receiptModels = FXUtils.getMapper(factory, ReceiptMapper.class, ReceiptMapper::selectAll, selection);
                // 按ID查询全部的明细
                List<ReceiptModelFull> fulls = new ArrayList<>();
                for (ReceiptModelFull modelFull : receiptModels) {
                    modelFull.setDetailList(FXUtils.getMapper(factory, ReceiptDetailMapper.class, ReceiptDetailMapper::selectAll, modelFull.getId()));
                    fulls.add(modelFull);
                }
                return fulls;
            }

            @Override
            public List<AnnualTotalModel> supAnnualTotal(Long sid, Long year) throws Exception {
                ReceiptSelection selection = new ReceiptSelection();
                selection.setYear(year);
                selection.setSid(sid);
                return FXUtils.getMapper(factory, ReceiptMapper.class, ReceiptMapper::supAnnualTotal, selection);
            }

            @Override
            public List<MonStatModel> takeStat(Long year) throws Exception {
                return FXUtils.getMapper(factory, ReceiptMapper.class, ReceiptMapper::takeStat, year.toString());
            }

            @Override
            public void insert(ReceiptModelFull model) throws Exception {
                ReceiptModel receiptModel = model.getReceiptModel();
                FXUtils.getMapper(factory, ReceiptMapper.class, ReceiptMapper::insert, receiptModel);

                // 回设 RID
                model.setId(receiptModel.getId());

                for (ReceiptDetailModel detail : model.getDetailList()) {
                    FXUtils.getMapper(factory, ReceiptDetailMapper.class, ReceiptDetailMapper::insert, detail);
                }
            }

            @Override
            public void update(ReceiptModelFull model) throws Exception {
                delete(new ReceiptModel(model));
                insert(model);
            }

            @Override
            public void delete(ReceiptModel model) throws Exception {
                FXUtils.getMapper(factory, ReceiptMapper.class, ReceiptMapper::delete, model);
            }

            @Override
            public List<Long> selectYear() throws Exception {
                return FXUtils.getMapper(factory, ReceiptMapper.class, ReceiptMapper::selectYear);
            }
        };
    }

    @Override
    public SupAnnualService getSupAnnualService() {
        return new SupAnnualService() {
            @Override
            public SupAnnualModelFull selectAnnual(Long sid, Long year) throws Exception {
                SupAnnualModelFull modelFull = new SupAnnualModelFull();
                AnnualSelection selection = new AnnualSelection(sid, year);

                // 查询对账表记录
                SupAnnualModel annualModel = FXUtils.getMapper(factory, SupAnnuMapper.class, SupAnnuMapper::selectAnnual, selection);
                if (annualModel == null) {
                    // 若查无此信息则插入
                    annualModel = new SupAnnualModel();
                    annualModel.setSid(sid);
                    annualModel.setYear(year);
                    FXUtils.getMapper(factory, SupAnnuMapper.class, SupAnnuMapper::insertAnnual, annualModel);
                }
                modelFull.set(annualModel);


                // 查询月份的原有记录，并使用 Map 作为存储
                List<SupAnnualMonModel> mons = FXUtils.getMapper(factory, SupAnnuMapper.class, SupAnnuMapper::selectMon, selection);
                Map<Long, SupAnnualMonModel> monMap = mons.stream()
                        .peek(s -> s.setValid(true))
                        .collect(Collectors.toMap(SupAnnualMonModel::getMon, e -> e));

                // 查询月份的采购总额
                ReceiptSelection receiptSelection = new ReceiptSelection();
                receiptSelection.setSid(sid);
                receiptSelection.setYear(year);
                List<AnnualTotalModel> monTotals = FXUtils.getMapper(factory, ReceiptMapper.class, ReceiptMapper::supAnnualTotal, receiptSelection);
                monTotals.forEach(m -> monMap.compute(m.getMon(), (k, v) -> {
                    if (v == null) {
                        v = new SupAnnualMonModel();
                    }
                    v.setTotal(m.getTotal());
                    v.setMon(m.getMon());
                    return v;
                }));

                LongStream.range(1, 13).forEach(m -> monMap.compute(m, (k, v) -> {
                    if (v == null) {
                        v = new SupAnnualMonModel();
                        v.setMon(k);
                    }
                    return v;
                }));

                modelFull.addMon(monMap.values());
                return modelFull;
            }

            @Override
            public void updateAnnual(SupAnnualModel model) throws Exception {

            }

            @Override
            public List<MonStatModel> payStat(Long year) throws Exception {
                return FXUtils.getMapper(factory, SupAnnuMapper.class, SupAnnuMapper::payStat, year);
            }

            @Override
            public List<TPStatModel> tpStat(Long year) throws Exception {
                return FXUtils.getMapper(factory, SupAnnuMapper.class, SupAnnuMapper::tpStat, year);
            }

            @Override
            public void updateMon(SupAnnualMonModel model) throws Exception {
                if (model.isValid()) {
                    FXUtils.getMapper(factory, SupAnnuMapper.class, SupAnnuMapper::updateMon, model);
                } else {
                    FXUtils.getMapper(factory, SupAnnuMapper.class, SupAnnuMapper::insertMon, model);
                }
            }
        };
    }

    @Override
    public OrderService getOrderService() {
        return new OrderService() {

            @Override
            public List<OrderModelFull> selectAll(OrderSelection selection) throws Exception {
                List<OrderModelFull> res = FXUtils.getMapper(factory, OrderMapper.class, OrderMapper::selectAll, selection);
                for (OrderModelFull modelFull : res) {
                    modelFull.setOrderItemModels(FXUtils.getMapper(factory, OrderItemMapper.class, OrderItemMapper::selectAllByOid, modelFull.getId()));
                }
                return res;
            }

            @Override
            public List<OrderModel> selectByCid(Long cid) throws Exception {
                return FXUtils.getMapper(factory,
                        OrderMapper.class,
                        OrderMapper::selectAll,
                        new OrderSelection(cid, null, null, null)
                ).stream().map(OrderModelFull::get).collect(Collectors.toList());
            }

            @Override
            public List<AnnualTotalModel> supAnnualTotal(Long cid, Long year) throws Exception {
                OrderSelection selection = new OrderSelection();
                selection.setYear(year);
                selection.setCid(cid);
                return FXUtils.getMapper(factory, OrderMapper.class, OrderMapper::supAnnualTotal, selection);
            }

            @Override
            public void insert(OrderModelFull model) throws Exception {
                LocalDate d = FXUtils.stampToLocalDate(model.getOrderdate());
                List<OrderModelFull> res = FXUtils.getMapper(factory, OrderMapper.class, OrderMapper::selectAll,
                        new OrderSelection(null, (long) d.getYear(), (long) d.getMonthValue(), null));
                OptionalLong m = res.stream().mapToLong(x -> Long.valueOf(x.getSerial())).max();
                if (m.isPresent()) {
                    model.setSerial("" + (m.getAsLong() + 1));
                } else {
                    model.setSerial(QSUtil.orderSerial(model.getOrderdate(), 1L));
                }
                OrderModel orderModel = model.get();
                FXUtils.getMapper(factory, OrderMapper.class, OrderMapper::insert, orderModel);

                // 回设 RID
                model.setId(orderModel.getId());

                for (OrderItemModel item : model.getOrderItemModels()) {
                    FXUtils.getMapper(factory, OrderItemMapper.class, OrderItemMapper::insert, item);
                }
            }

            @Override
            public void update(OrderModelFull model) throws Exception {
                FXUtils.getMapper(factory, OrderMapper.class, OrderMapper::update, model.get());
                FXUtils.getMapper(factory, OrderItemMapper.class, OrderItemMapper::deleteByOid, model.getId());
                for (OrderItemModel item : model.getOrderItemModels()) {
                    FXUtils.getMapper(factory, OrderItemMapper.class, OrderItemMapper::insert, item);
                }
            }

            @Override
            public void delete(OrderModel model) throws Exception {
                FXUtils.getMapper(factory, OrderItemMapper.class, OrderItemMapper::deleteByOid, model.getId());
                FXUtils.getMapper(factory, OrderMapper.class, OrderMapper::delete, model);
            }

            @Override
            public List<Long> selectYear() throws Exception {
                return FXUtils.getMapper(factory, OrderMapper.class, OrderMapper::selectYear);
            }

            @Override
            public List<OrderItemModel> selectAllByOid(Long oid) throws Exception {
                return FXUtils.getMapper(factory, OrderItemMapper.class, OrderItemMapper::selectAllByOid, oid);
            }
        };
    }

    @Override
    public DeliveryService getDeliveryService() {
        return new DeliveryService() {
            @Override
            public List<DeliveryModelFull> selectAll(DeliverySelection selection) throws Exception {
                List<DeliveryModelFull> res = FXUtils.getMapper(factory, DeliveryMapper.class, DeliveryMapper::selectAll, selection);
                for (DeliveryModelFull modelFull : res) {
                    modelFull.setDeliveryItemModels(FXUtils.getMapper(factory, DeliveryItemMapper.class, DeliveryItemMapper::selectByDid, modelFull.getId()));
                }
                return res;
            }

            @Override
            public DeliveryModelFull selectByID(Long id) throws Exception {
                DeliveryModelFull res = FXUtils.getMapper(factory, DeliveryMapper.class, DeliveryMapper::selectByID, id);
                res.setDeliveryItemModels(FXUtils.getMapper(factory, DeliveryItemMapper.class, DeliveryItemMapper::selectByDid, res.getId()));
                return res;
            }

            @Override
            public void insert(DeliveryModelFull model) throws Exception {
                System.out.println("public void insert(DeliveryModelFull model) throws Exception : " + model.getCid());
                LocalDate d = FXUtils.stampToLocalDate(model.getDdate());
                List<DeliveryModelFull> res = FXUtils.getMapper(factory, DeliveryMapper.class, DeliveryMapper::selectAll, new DeliverySelection(null, (long) d.getYear(), (long) d.getMonthValue(), null));
                OptionalLong m = res.stream().mapToLong(x -> Long.valueOf(x.getSerial())).max();
                if (m.isPresent()) {
                    model.setSerial("" + (m.getAsLong() + 1));
                } else {
                    model.setSerial(QSUtil.orderSerial(model.getDdate(), 1L));
                }
                DeliveryModel x = model.get();
                FXUtils.getMapper(factory, DeliveryMapper.class, DeliveryMapper::insert, x);
                // 回设 RID
                model.setId(x.getId());
                for (DeliveryItemModel item : model.getDeliveryItemModels()) {
                    FXUtils.getMapper(factory, DeliveryItemMapper.class, DeliveryItemMapper::insert, item);
                }
            }

            @Override
            public void update(DeliveryModelFull model) throws Exception {
                FXUtils.getMapper(factory, DeliveryMapper.class, DeliveryMapper::update, model.get());
                FXUtils.getMapper(factory, DeliveryItemMapper.class, DeliveryItemMapper::deleteByDid, model.getId());
                for (DeliveryItemModel item : model.getDeliveryItemModels()) {
                    FXUtils.getMapper(factory, DeliveryItemMapper.class, DeliveryItemMapper::insert, item);
                }
            }

            @Override
            public void delete(DeliveryModel model) throws Exception {
                FXUtils.getMapper(factory, DeliveryItemMapper.class, DeliveryItemMapper::deleteByDid, model.getId());
                FXUtils.getMapper(factory, DeliveryMapper.class, DeliveryMapper::delete, model);
            }
        };
    }

    @Override
    public CustAnnualService getCustAnnualService() {
        return new CustAnnualService() {
            @Override
            public CustAnnualModelFull selectAnnual(Long cid, Long year) throws Exception {
                CustAnnualModelFull modelFull = new CustAnnualModelFull();
                AnnualSelection selection = new AnnualSelection(cid, year);

                // 查询对账表记录
                CustAnnualModel annualModel = FXUtils.getMapper(factory, CustAnnuMapper.class, CustAnnuMapper::selectAnnual, selection);
                if (annualModel == null) {
                    // 若查无此信息则插入
                    annualModel = new CustAnnualModel();
                    annualModel.setCid(cid);
                    annualModel.setYear(year);
                    FXUtils.getMapper(factory, CustAnnuMapper.class, CustAnnuMapper::insertAnnual, annualModel);
                }
                modelFull.set(annualModel);


                // 查询月份的原有记录，并使用 Map 作为存储
                List<CustAnnualMonModel> mons = FXUtils.getMapper(factory, CustAnnuMapper.class, CustAnnuMapper::selectMon, selection);
                Map<Long, CustAnnualMonModel> monMap = mons.stream()
                        .peek(s -> s.setValid(true))
                        .collect(Collectors.toMap(CustAnnualMonModel::getMon, e -> e));

                // 查询月份的采购总额
                DeliverySelection cy = new DeliverySelection();
                cy.setCid(cid);
                cy.setYear(year);
                List<AnnualTotalModel> monTotals = FXUtils.getMapper(factory, DeliveryMapper.class, DeliveryMapper::supAnnualTotal, cy);
                monTotals.forEach(m -> monMap.compute(m.getMon(), (k, v) -> {
                    if (v == null) {
                        v = new CustAnnualMonModel();
                    }
                    v.setTotal(m.getTotal());
                    v.setMon(m.getMon());
                    return v;
                }));

                LongStream.range(1, 13).forEach(m -> monMap.compute(m, (k, v) -> {
                    if (v == null) {
                        v = new CustAnnualMonModel();
                        v.setMon(k);
                    }
                    return v;
                }));

                modelFull.addMon(monMap.values());
                return modelFull;
            }

            @Override
            public void updateAnnual(CustAnnualModel model) throws Exception {

            }

            @Override
            public void updateMon(CustAnnualMonModel model) throws Exception {
                if (model.isValid()) {
                    FXUtils.getMapper(factory, CustAnnuMapper.class, CustAnnuMapper::updateMon, model);
                } else {
                    FXUtils.getMapper(factory, CustAnnuMapper.class, CustAnnuMapper::insertMon, model);
                }
            }
        };
    }
}
