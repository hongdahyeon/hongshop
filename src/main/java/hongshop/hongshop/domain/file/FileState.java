package hongshop.hongshop.domain.file;

/**
* @fileName FileState
* @author dahyeon
* @version 1.0.0
* @date 2023-07-18
* @summary  (1) SAVED : 저장됨
 *          (2) PROCESS : 저장 중 -> 후에 최종 저징 시, FILE-ID를 통해 UPDATE
**/

public enum FileState {
    SAVED, PROCESS
}
