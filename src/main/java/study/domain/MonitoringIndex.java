package study.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import study.index_enum.IndexEnum;

/**
 * 监控指标父类
 */
@Data
@NoArgsConstructor
public abstract class MonitoringIndex {
    /*指标唯一标识*/
    private IndexEnum indexFlag;
    /*指标名称*/
    private String indexName;
    /*指标值*/
    private String indexValue;
    /*显示格式*/
    private DisplayForm displayForm;
    /*采样频率*/
    private int samplingFrequency;
}
